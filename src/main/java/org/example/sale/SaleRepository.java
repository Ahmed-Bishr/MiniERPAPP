package org.example.sale;

import org.example.config.DatabaseConnector;
import org.example.dto.SaleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access for sales.
 *
 * <p>{@link #createSale} runs the wholesale as ONE atomic transaction
 * on a single connection: read price, check stock, insert the sale header,
 * read the generated id, insert the sale item, reduce inventory, then
 * commit. On any validation problem it rolls back and returns a
 * {@link CreateSaleStatus}; on any SQL error it rolls back and rethrows.</p>
 *
 * <p>No UI code in this class.</p>
 */
public class SaleRepository {

    public CreateSaleResult createSale(
            int branchId, int employeeId, int customerId, int cashRegisterId,
            int warehouseId, int productId, int quantity, String paymentMethod) throws SQLException {

        Connection connection = null;

        try {
            connection = DatabaseConnector.connect();
            connection.setAutoCommit(false);

            // 1) Read product price
            double sellingPrice = 0;

            String productSql =
                    "SELECT selling_price FROM products WHERE product_id = ?";

            PreparedStatement productStatement =
                    connection.prepareStatement(productSql);

            productStatement.setInt(1, productId);
            ResultSet productRs = productStatement.executeQuery();

            if (!productRs.next()) {
                productRs.close();
                productStatement.close();
                connection.rollback();
                connection.close();
                return CreateSaleResult.failure(CreateSaleStatus.PRODUCT_NOT_FOUND);
            }

            sellingPrice = productRs.getDouble("selling_price");
            productRs.close();
            productStatement.close();

            // 2) Check stock
            String stockSql =
                    "SELECT quantity FROM inventory "
                    + "WHERE warehouse_id = ? AND product_id = ?";

            PreparedStatement stockStatement =
                    connection.prepareStatement(stockSql);

            stockStatement.setInt(1, warehouseId);
            stockStatement.setInt(2, productId);

            ResultSet stockRs = stockStatement.executeQuery();

            if (!stockRs.next()) {
                stockRs.close();
                stockStatement.close();
                connection.rollback();
                connection.close();
                return CreateSaleResult.failure(CreateSaleStatus.INVENTORY_NOT_FOUND);
            }

            int available = stockRs.getInt("quantity");
            stockRs.close();
            stockStatement.close();

            if (quantity <= 0 || quantity > available) {
                connection.rollback();
                connection.close();
                return CreateSaleResult.failure(CreateSaleStatus.NOT_ENOUGH_STOCK);
            }

            double total = sellingPrice * quantity;

            // 3) Insert sale header and return generated sale_id
            String saleSql =
                    "INSERT INTO sales "
                    + "(branch_id, employee_id, customer_id, cash_register_id, "
                    + "discount, tax, total, payment_method, status) "
                    + "VALUES (?, ?, ?, ?, 0, 0, ?, ?, 'Completed')";

            PreparedStatement saleStatement =
                    connection.prepareStatement(
                            saleSql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            saleStatement.setInt(1, branchId);
            saleStatement.setInt(2, employeeId);
            saleStatement.setInt(3, customerId);
            saleStatement.setInt(4, cashRegisterId);
            saleStatement.setDouble(5, total);
            saleStatement.setString(6, paymentMethod);

            saleStatement.executeUpdate();

            ResultSet keys = saleStatement.getGeneratedKeys();

            if (!keys.next()) {
                keys.close();
                saleStatement.close();
                connection.rollback();
                connection.close();
                return CreateSaleResult.failure(CreateSaleStatus.COULD_NOT_CREATE_SALE);
            }

            int saleId = keys.getInt(1);

            keys.close();
            saleStatement.close();

            // 4) Insert sale item
            String itemSql =
                    "INSERT INTO sale_items "
                    + "(sale_id, product_id, quantity, unit_price, discount) "
                    + "VALUES (?, ?, ?, ?, 0)";

            PreparedStatement itemStatement =
                    connection.prepareStatement(itemSql);

            itemStatement.setInt(1, saleId);
            itemStatement.setInt(2, productId);
            itemStatement.setInt(3, quantity);
            itemStatement.setDouble(4, sellingPrice);

            itemStatement.executeUpdate();
            itemStatement.close();

            // 5) Reduce inventory
            String inventorySql =
                    "UPDATE inventory "
                    + "SET quantity = quantity - ? "
                    + "WHERE warehouse_id = ? AND product_id = ?";

            PreparedStatement inventoryStatement =
                    connection.prepareStatement(inventorySql);

            inventoryStatement.setInt(1, quantity);
            inventoryStatement.setInt(2, warehouseId);
            inventoryStatement.setInt(3, productId);

            inventoryStatement.executeUpdate();
            inventoryStatement.close();

            connection.commit();
            connection.close();

            return CreateSaleResult.success(saleId, total);

        } catch (SQLException ex) {

            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException ignored) {
                }
            }

            throw ex;
        }
    }

    public List<SaleDTO> getAllSales() throws SQLException {
        String sql =
                "SELECT s.sale_id, c.name AS customer_name, "
                + "e.name AS employee_name, s.total, "
                + "s.payment_method, s.status "
                + "FROM sales s "
                + "LEFT JOIN customers c ON s.customer_id = c.customer_id "
                + "JOIN employees e ON s.employee_id = e.employee_id "
                + "ORDER BY s.sale_id";

        List<SaleDTO> sales = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                sales.add(new SaleDTO(
                        rs.getInt("sale_id"),
                        rs.getString("customer_name"),
                        rs.getString("employee_name"),
                        rs.getDouble("total"),
                        rs.getString("payment_method"),
                        rs.getString("status")
                ));
            }
        }

        return sales;
    }
}
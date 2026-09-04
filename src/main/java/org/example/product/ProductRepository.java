package org.example.product;

import org.example.config.DatabaseConnector;
import org.example.dto.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access for products. All SQL and ResultSet-to-DTO mapping
 * lives here. Throws {@link SQLException} so the menu layer can show
 * a consistent error dialog. No UI code in this class.
 */
public class ProductRepository {

    public void addProduct(ProductDTO product) throws SQLException {
        String sql =
                "INSERT INTO products "
                + "(name, category, barcode, purchase_price, selling_price) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setString(3, product.getBarcode());
            statement.setDouble(4, product.getPurchasePrice());
            statement.setDouble(5, product.getSellingPrice());

            statement.executeUpdate();
        }
    }

    public List<ProductDTO> getAllProducts() throws SQLException {
        String sql =
                "SELECT product_id, name, category, barcode, "
                + "purchase_price, selling_price "
                + "FROM products ORDER BY product_id";

        List<ProductDTO> products = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("barcode"),
                        rs.getDouble("purchase_price"),
                        rs.getDouble("selling_price")
                ));
            }
        }

        return products;
    }

    public ProductDTO getProductById(int id) throws SQLException {
        String sql =
                "SELECT product_id, name, category, barcode, "
                + "purchase_price, selling_price "
                + "FROM products WHERE product_id = ?";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new ProductDTO(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("barcode"),
                            rs.getDouble("purchase_price"),
                            rs.getDouble("selling_price")
                    );
                }
            }
        }

        return null;
    }

    public boolean updateSellingPrice(int id, double price) throws SQLException {
        String sql =
                "UPDATE products SET selling_price = ? "
                + "WHERE product_id = ?";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, price);
            statement.setInt(2, id);

            return statement.executeUpdate() == 1;
        }
    }
}
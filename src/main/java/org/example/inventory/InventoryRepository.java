package org.example.inventory;

import org.example.config.DatabaseConnector;
import org.example.dto.InventoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access for inventory. All SQL and ResultSet-to-DTO mapping
 * lives here. Throws {@link SQLException} so the menu layer can show
 * a consistent error dialog. No UI code in this class.
 */
public class InventoryRepository {

    public List<InventoryDTO> getAllInventory() throws SQLException {
        String sql =
                "SELECT w.name AS warehouse_name, p.product_id, "
                + "p.name AS product_name, i.quantity, i.minimum_quantity "
                + "FROM inventory i "
                + "JOIN warehouses w ON i.warehouse_id = w.warehouse_id "
                + "JOIN products p ON i.product_id = p.product_id "
                + "ORDER BY w.warehouse_id, p.product_id";

        List<InventoryDTO> items = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new InventoryDTO(
                        rs.getString("warehouse_name"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getInt("minimum_quantity")
                ));
            }
        }

        return items;
    }

    public boolean addStock(int warehouseId, int productId, int amount) throws SQLException {
        String sql =
                "UPDATE inventory SET quantity = quantity + ? "
                + "WHERE warehouse_id = ? AND product_id = ?";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, amount);
            statement.setInt(2, warehouseId);
            statement.setInt(3, productId);

            return statement.executeUpdate() == 1;
        }
    }
}
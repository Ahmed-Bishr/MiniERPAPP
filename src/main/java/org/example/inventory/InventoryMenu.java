package org.example.inventory;

import org.example.common.DatabaseErrorHandler;
import org.example.dto.InventoryDTO;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * JOptionPane user interface for the inventory module.
 * Handles input, dialogs, and calling {@link InventoryService}.
 * No SQL and no JDBC code here.
 */
public class InventoryMenu {

    private final InventoryService service;

    public InventoryMenu(InventoryService service) {
        this.service = service;
    }

    public void showMenu() {
        int choice = 0;

        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "INVENTORY MANAGEMENT\n\n"
                    + "1. View Inventory\n"
                    + "2. Add Stock\n"
                    + "3. Back\n\n"
                    + "Choose:"
            );

            if (input == null) {
                choice = 3;
            } else {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        showInventory();
                        break;
                    case 2:
                        addStock();
                        break;
                    case 3:
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice");
                }
            }

        } while (choice != 3);
    }

    private void showInventory() {
        String result = "INVENTORY\n\n";

        try {
            List<InventoryDTO> items = service.getAllInventory();

            for (InventoryDTO item : items) {
                result = result
                        + item.getWarehouseName()
                        + " | " + item.getProductId()
                        + " - " + item.getProductName()
                        + " | Qty: " + item.getQuantity();

                if (item.getQuantity() <= item.getMinimumQuantity()) {
                    result = result + " | LOW STOCK";
                }

                result = result + "\n";
            }

            JOptionPane.showMessageDialog(null, result);
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void addStock() {
        int warehouseId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Warehouse ID:")
        );

        int productId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Product ID:")
        );

        int amount = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Quantity to add:")
        );

        try {
            if (service.addStock(warehouseId, productId, amount)) {
                JOptionPane.showMessageDialog(null, "Stock updated");
            } else {
                JOptionPane.showMessageDialog(null, "Inventory record not found");
            }
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }
}
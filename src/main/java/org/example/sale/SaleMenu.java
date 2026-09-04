package org.example.sale;

import org.example.common.DatabaseErrorHandler;
import org.example.dto.SaleDTO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * JOptionPane user interface for the sales module.
 * Handles input, dialogs, and calling {@link SaleService}.
 * No SQL and no JDBC code here.
 */
public class SaleMenu {

    private final SaleService service;

    public SaleMenu(SaleService service) {
        this.service = service;
    }

    public void createSale() {
        int branchId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Branch ID:")
        );

        int employeeId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Employee ID:")
        );

        int customerId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Customer ID:")
        );

        int cashRegisterId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Cash Register ID:")
        );

        int warehouseId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Warehouse ID:")
        );

        int productId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Product ID:")
        );

        int quantity = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Quantity:")
        );

        String paymentMethod = JOptionPane.showInputDialog(
                null,
                "Payment Method (Cash/Card):"
        );

        try {
            CreateSaleResult result = service.createSale(
                    branchId, employeeId, customerId, cashRegisterId,
                    warehouseId, productId, quantity, paymentMethod
            );

            switch (result.getStatus()) {
                case SUCCESS:
                    JOptionPane.showMessageDialog(
                            null,
                            "Sale #" + result.getSaleId()
                                    + " completed successfully\nTotal = " + result.getTotal()
                    );
                    break;
                case PRODUCT_NOT_FOUND:
                    JOptionPane.showMessageDialog(null, "Product not found");
                    break;
                case INVENTORY_NOT_FOUND:
                    JOptionPane.showMessageDialog(null, "Inventory record not found");
                    break;
                case NOT_ENOUGH_STOCK:
                    JOptionPane.showMessageDialog(null, "Not enough stock");
                    break;
                case COULD_NOT_CREATE_SALE:
                    JOptionPane.showMessageDialog(null, "Could not create sale");
                    break;
            }
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    public void showSales() {
        String result = "SALES\n\n";

        try {
            List<SaleDTO> sales = service.getAllSales();

            for (SaleDTO sale : sales) {
                result = result
                        + "Sale #" + sale.getSaleId()
                        + " | " + sale.getCustomerName()
                        + " | " + sale.getEmployeeName()
                        + " | Total: " + sale.getTotal()
                        + " | " + sale.getPaymentMethod()
                        + " | " + sale.getStatus()
                        + "\n";
            }

            JOptionPane.showMessageDialog(null, result);
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }
}
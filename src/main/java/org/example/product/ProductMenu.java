package org.example.product;

import org.example.common.DatabaseErrorHandler;
import org.example.dto.ProductDTO;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * JOptionPane user interface for the products module.
 * Handles input, dialogs, and calling {@link ProductService}.
 * No SQL and no JDBC code here.
 */
public class ProductMenu {

    private final ProductService service;

    public ProductMenu(ProductService service) {
        this.service = service;
    }

    public void showMenu() {
        int choice = 0;

        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "PRODUCT MANAGEMENT\n\n"
                    + "1. Add Product\n"
                    + "2. View Products\n"
                    + "3. Search Product\n"
                    + "4. Update Selling Price\n"
                    + "5. Back\n\n"
                    + "Choose:"
            );

            if (input == null) {
                choice = 5;
            } else {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        showProducts();
                        break;
                    case 3:
                        searchProduct();
                        break;
                    case 4:
                        updateProductPrice();
                        break;
                    case 5:
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice");
                }
            }

        } while (choice != 5);
    }

    private void addProduct() {
        String name = JOptionPane.showInputDialog(null, "Product Name:");
        String category = JOptionPane.showInputDialog(null, "Category:");
        String barcode = JOptionPane.showInputDialog(null, "Barcode:");

        double purchasePrice = Double.parseDouble(
                JOptionPane.showInputDialog(null, "Purchase Price:")
        );

        double sellingPrice = Double.parseDouble(
                JOptionPane.showInputDialog(null, "Selling Price:")
        );

        try {
            service.addProduct(new ProductDTO(
                    0, name, category, barcode, purchasePrice, sellingPrice
            ));
            JOptionPane.showMessageDialog(null, "Product added successfully");
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void showProducts() {
        String result = "PRODUCTS\n\n";

        try {
            List<ProductDTO> products = service.getAllProducts();

            for (ProductDTO product : products) {
                result = result
                        + "ID: " + product.getProductId()
                        + " | " + product.getName()
                        + " | " + product.getCategory()
                        + " | Sell: " + product.getSellingPrice()
                        + "\n";
            }

            JOptionPane.showMessageDialog(null, result);
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void searchProduct() {
        int id = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Product ID:")
        );

        try {
            ProductDTO product = service.findProduct(id);

            if (product != null) {
                JOptionPane.showMessageDialog(null, formatProductSummary(product));
            } else {
                JOptionPane.showMessageDialog(null, "Product not found");
            }
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void updateProductPrice() {
        int id = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Product ID:")
        );

        double price = Double.parseDouble(
                JOptionPane.showInputDialog(null, "New Selling Price:")
        );

        try {
            if (service.updatePrice(id, price)) {
                JOptionPane.showMessageDialog(null, "Price updated");
            } else {
                JOptionPane.showMessageDialog(null, "Product not found");
            }
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private String formatProductSummary(ProductDTO product) {
        return "Product ID: " + product.getProductId()
                + "\nName: " + product.getName()
                + "\nCategory: " + product.getCategory()
                + "\nBarcode: " + product.getBarcode()
                + "\nPurchase Price: " + product.getPurchasePrice()
                + "\nSelling Price: " + product.getSellingPrice();
    }
}
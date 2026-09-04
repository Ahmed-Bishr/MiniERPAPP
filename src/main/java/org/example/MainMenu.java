package org.example;

import org.example.customer.CustomerMenu;
import org.example.employee.EmployeeMenu;
import org.example.inventory.InventoryMenu;
import org.example.product.ProductMenu;
import org.example.sale.SaleMenu;

import javax.swing.JOptionPane;

/**
 * The main application menu. Only handles the top-level choice loop and
 * delegates each option to the matching module menu. No SQL, no JDBC.
 */
public class MainMenu {

    private final ProductMenu productMenu;
    private final CustomerMenu customerMenu;
    private final EmployeeMenu employeeMenu;
    private final InventoryMenu inventoryMenu;
    private final SaleMenu saleMenu;

    public MainMenu(ProductMenu productMenu,
                    CustomerMenu customerMenu,
                    EmployeeMenu employeeMenu,
                    InventoryMenu inventoryMenu,
                    SaleMenu saleMenu) {
        this.productMenu = productMenu;
        this.customerMenu = customerMenu;
        this.employeeMenu = employeeMenu;
        this.inventoryMenu = inventoryMenu;
        this.saleMenu = saleMenu;
    }

    public void show() {
        int choice = 0;

        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "MINI ERP - POSTGRESQL\n\n"
                    + "1. Products\n"
                    + "2. Customers\n"
                    + "3. Employees\n"
                    + "4. Inventory\n"
                    + "5. Create Sale\n"
                    + "6. View Sales\n"
                    + "7. Exit\n\n"
                    + "Choose:"
            );

            if (input == null) {
                choice = 7;
            } else {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        productMenu.showMenu();
                        break;
                    case 2:
                        customerMenu.showMenu();
                        break;
                    case 3:
                        employeeMenu.showMenu();
                        break;
                    case 4:
                        inventoryMenu.showMenu();
                        break;
                    case 5:
                        saleMenu.createSale();
                        break;
                    case 6:
                        saleMenu.showSales();
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(null, "Goodbye");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice");
                }
            }

        } while (choice != 7);
    }
}
package org.example;

import org.example.config.DatabaseConnector;
import org.example.customer.CustomerMenu;
import org.example.customer.CustomerRepository;
import org.example.customer.CustomerService;
import org.example.employee.EmployeeMenu;
import org.example.employee.EmployeeRepository;
import org.example.employee.EmployeeService;
import org.example.inventory.InventoryMenu;
import org.example.inventory.InventoryRepository;
import org.example.inventory.InventoryService;
import org.example.product.ProductMenu;
import org.example.product.ProductRepository;
import org.example.product.ProductService;
import org.example.sale.SaleMenu;
import org.example.sale.SaleRepository;
import org.example.sale.SaleService;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Application entry point. Wires up the repositories, services and menus,
 * checks that the database is reachable, and starts the main menu.
 */
public class Main {

    public static void main(String[] args) {

        if (!canConnectToDatabase()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Could not connect to the PostgreSQL database.\n"
                    + "Check that PostgreSQL is running and the settings in "
                    + "src/main/resources/application.properties are correct."
            );
            return;
        }

        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductMenu productMenu = new ProductMenu(productService);

        CustomerRepository customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerMenu customerMenu = new CustomerMenu(customerService);

        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        EmployeeMenu employeeMenu = new EmployeeMenu(employeeService);

        InventoryRepository inventoryRepository = new InventoryRepository();
        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryMenu inventoryMenu = new InventoryMenu(inventoryService);

        SaleRepository saleRepository = new SaleRepository();
        SaleService saleService = new SaleService(saleRepository);
        SaleMenu saleMenu = new SaleMenu(saleService);

        MainMenu mainMenu = new MainMenu(
                productMenu,
                customerMenu,
                employeeMenu,
                inventoryMenu,
                saleMenu
        );

        mainMenu.show();
    }

    private static boolean canConnectToDatabase() {
        try (Connection connection = DatabaseConnector.connect()) {
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database Error:\n" + ex.getMessage()
            );
            return false;
        }
    }
}
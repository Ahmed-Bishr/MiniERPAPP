package org.example.customer;

import org.example.common.DatabaseErrorHandler;
import org.example.dto.CustomerDTO;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * JOptionPane user interface for the customers module.
 * Handles input, dialogs, and calling {@link CustomerService}.
 * No SQL and no JDBC code here.
 */
public class CustomerMenu {

    private final CustomerService service;

    public CustomerMenu(CustomerService service) {
        this.service = service;
    }

    public void showMenu() {
        int choice = 0;

        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "CUSTOMER MANAGEMENT\n\n"
                    + "1. Add Customer\n"
                    + "2. View Customers\n"
                    + "3. Back\n\n"
                    + "Choose:"
            );

            if (input == null) {
                choice = 3;
            } else {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        showCustomers();
                        break;
                    case 3:
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice");
                }
            }

        } while (choice != 3);
    }

    private void addCustomer() {
        String name = JOptionPane.showInputDialog(null, "Customer Name:");
        String phone = JOptionPane.showInputDialog(null, "Phone:");

        try {
            service.addCustomer(new CustomerDTO(0, name, phone));
            JOptionPane.showMessageDialog(null, "Customer added successfully");
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void showCustomers() {
        String result = "CUSTOMERS\n\n";

        try {
            List<CustomerDTO> customers = service.getAllCustomers();

            for (CustomerDTO customer : customers) {
                result = result
                        + customer.getCustomerId() + " - "
                        + customer.getName() + " - "
                        + customer.getPhone() + "\n";
            }

            JOptionPane.showMessageDialog(null, result);
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }
}
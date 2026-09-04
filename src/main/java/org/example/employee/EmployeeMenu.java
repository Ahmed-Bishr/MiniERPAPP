package org.example.employee;

import org.example.common.DatabaseErrorHandler;
import org.example.dto.EmployeeDTO;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * JOptionPane user interface for the employees module.
 * Handles input, dialogs, and calling {@link EmployeeService}.
 * No SQL and no JDBC code here.
 */
public class EmployeeMenu {

    private final EmployeeService service;

    public EmployeeMenu(EmployeeService service) {
        this.service = service;
    }

    public void showMenu() {
        int choice = 0;

        do {
            String input = JOptionPane.showInputDialog(
                    null,
                    "EMPLOYEE MANAGEMENT\n\n"
                    + "1. Add Employee\n"
                    + "2. View Employees\n"
                    + "3. Fire Employee\n"
                    + "4. Back\n\n"
                    + "Choose:"
            );

            if (input == null) {
                choice = 4;
            } else {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        showEmployees();
                        break;
                    case 3:
                        fireEmployee();
                        break;
                    case 4:
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice");
                }
            }

        } while (choice != 4);
    }

    private void addEmployee() {
        int branchId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Branch ID:")
        );

        int roleId = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Role ID:")
        );

        String name = JOptionPane.showInputDialog(null, "Employee Name:");
        String jobTitle = JOptionPane.showInputDialog(null, "Job Title:");

        double salary = Double.parseDouble(
                JOptionPane.showInputDialog(null, "Salary:")
        );

        String phone = JOptionPane.showInputDialog(null, "Phone:");
        String hireDate = JOptionPane.showInputDialog(
                null,
                "Hire Date (YYYY-MM-DD):"
        );

        String password = JOptionPane.showInputDialog(null, "Password:");

        try {
            EmployeeDTO employee = new EmployeeDTO(
                    0, branchId, roleId, name, jobTitle, salary,
                    phone, hireDate, password, "", "", ""
            );

            service.addEmployee(employee);
            JOptionPane.showMessageDialog(null, "Employee added successfully");
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void showEmployees() {
        String result = "EMPLOYEES\n\n";

        try {
            List<EmployeeDTO> employees = service.getAllEmployees();

            for (EmployeeDTO employee : employees) {
                result = result
                        + "ID: " + employee.getEmployeeId()
                        + " | " + employee.getName()
                        + " | " + employee.getJobTitle()
                        + " | " + employee.getBranchName()
                        + " | " + employee.getRoleName()
                        + " | " + employee.getStatus()
                        + "\n";
            }

            JOptionPane.showMessageDialog(null, result);
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }

    private void fireEmployee() {
        int id = Integer.parseInt(
                JOptionPane.showInputDialog(null, "Employee ID to fire:")
        );

        try {
            if (service.fireEmployee(id)) {
                JOptionPane.showMessageDialog(null, "Employee fired successfully");
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Employee not found or already fired"
                );
            }
        } catch (SQLException ex) {
            DatabaseErrorHandler.showError(ex);
        }
    }
}
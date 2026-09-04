package org.example.employee;

import org.example.config.DatabaseConnector;
import org.example.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access for employees. All SQL and ResultSet-to-DTO mapping
 * lives here. Throws {@link SQLException} so the menu layer can show
 * a consistent error dialog. No UI code in this class.
 */
public class EmployeeRepository {

    public void addEmployee(EmployeeDTO employee) throws SQLException {
        String sql =
                "INSERT INTO employees "
                + "(branch_id, role_id, name, job_title, salary, phone, "
                + "hire_date, password, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Active')";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employee.getBranchId());
            statement.setInt(2, employee.getRoleId());
            statement.setString(3, employee.getName());
            statement.setString(4, employee.getJobTitle());
            statement.setDouble(5, employee.getSalary());
            statement.setString(6, employee.getPhone());
            statement.setString(7, employee.getHireDate());
            statement.setString(8, employee.getPassword());

            statement.executeUpdate();
        }
    }

    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        String sql =
                "SELECT e.employee_id, e.name, e.job_title, e.salary, e.status, "
                + "b.name AS branch_name, r.name AS role_name "
                + "FROM employees e "
                + "JOIN branches b ON e.branch_id = b.branch_id "
                + "JOIN roles r ON e.role_id = r.role_id "
                + "ORDER BY e.employee_id";

        List<EmployeeDTO> employees = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(new EmployeeDTO(
                        rs.getInt("employee_id"),
                        0,
                        0,
                        rs.getString("name"),
                        rs.getString("job_title"),
                        rs.getDouble("salary"),
                        "",
                        "",
                        "",
                        rs.getString("status"),
                        rs.getString("branch_name"),
                        rs.getString("role_name")
                ));
            }
        }

        return employees;
    }

    public boolean fireEmployee(int id) throws SQLException {


        String sql =
                "UPDATE employees SET status = 'Fired' "
                + "WHERE employee_id = ? AND status <> 'Fired'";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() == 1;
        }
    }
}
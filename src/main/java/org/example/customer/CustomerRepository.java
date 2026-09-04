package org.example.customer;

import org.example.config.DatabaseConnector;
import org.example.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database access for customers. All SQL and ResultSet-to-DTO mapping
 * lives here. Throws {@link SQLException} so the menu layer can show
 * a consistent error dialog. No UI code in this class.
 */
public class CustomerRepository {

    public void addCustomer(CustomerDTO customer) throws SQLException {
        String sql = "INSERT INTO customers (name, phone) VALUES (?, ?)";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());

            statement.executeUpdate();
        }
    }

    public List<CustomerDTO> getAllCustomers() throws SQLException {
        String sql =
                "SELECT customer_id, name, phone "
                + "FROM customers ORDER BY customer_id";

        List<CustomerDTO> customers = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(new CustomerDTO(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }
        }

        return customers;
    }
}
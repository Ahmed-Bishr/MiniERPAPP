package org.example.customer;

import org.example.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Application logic for customers. Delegates all database work to
 * {@link CustomerRepository} - no SQL lives here.
 */
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void addCustomer(CustomerDTO customer) throws SQLException {
        repository.addCustomer(customer);
    }

    public List<CustomerDTO> getAllCustomers() throws SQLException {
        return repository.getAllCustomers();
    }
}
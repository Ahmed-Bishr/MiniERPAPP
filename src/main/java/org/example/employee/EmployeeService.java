package org.example.employee;

import org.example.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Application logic for employees. Delegates all database work to
 * {@link EmployeeRepository} - no SQL lives here.
 */
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void addEmployee(EmployeeDTO employee) throws SQLException {
        repository.addEmployee(employee);
    }

    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        return repository.getAllEmployees();
    }

    public boolean fireEmployee(int id) throws SQLException {
        return repository.fireEmployee(id);
    }
}
package org.example.sale;

import org.example.dto.SaleDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Application logic for sales. Delegates all database work (including the
 * atomic create-sale transaction) to {@link SaleRepository} - no SQL here.
 */
public class SaleService {

    private final SaleRepository repository;

    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public CreateSaleResult createSale(
            int branchId, int employeeId, int customerId, int cashRegisterId,
            int warehouseId, int productId, int quantity, String paymentMethod) throws SQLException {

        return repository.createSale(
                branchId, employeeId, customerId, cashRegisterId,
                warehouseId, productId, quantity, paymentMethod
        );
    }

    public List<SaleDTO> getAllSales() throws SQLException {
        return repository.getAllSales();
    }
}
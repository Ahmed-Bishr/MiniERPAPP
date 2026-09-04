package org.example.inventory;

import org.example.dto.InventoryDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Application logic for inventory. Delegates all database work to
 * {@link InventoryRepository} - no SQL lives here.
 */
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryDTO> getAllInventory() throws SQLException {
        return repository.getAllInventory();
    }

    public boolean addStock(int warehouseId, int productId, int amount) throws SQLException {
        return repository.addStock(warehouseId, productId, amount);
    }
}
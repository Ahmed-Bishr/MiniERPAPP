package org.example.product;

import org.example.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Application logic for products. Delegates all database work to
 * {@link ProductRepository} - no SQL lives here.
 */
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(ProductDTO product) throws SQLException {
        repository.addProduct(product);
    }

    public List<ProductDTO> getAllProducts() throws SQLException {
        return repository.getAllProducts();
    }

    public ProductDTO findProduct(int id) throws SQLException {
        return repository.getProductById(id);
    }

    public boolean updatePrice(int id, double price) throws SQLException {
        return repository.updateSellingPrice(id, price);
    }
}
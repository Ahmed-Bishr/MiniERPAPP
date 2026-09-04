package org.example.dto;

/**
 * Data container for a single inventory row (joined with its
 * warehouse and product for display). Maps to the {@code inventory} table.
 * No SQL or business logic here.
 */
public class InventoryDTO {

    private String warehouseName;
    private int productId;
    private String productName;
    private int quantity;
    private int minimumQuantity;

    public InventoryDTO() {
        this("", 0, "", 0, 0);
    }

    public InventoryDTO(String warehouseName, int productId, String productName,
                        int quantity, int minimumQuantity) {
        this.warehouseName = warehouseName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getMinimumQuantity() { return minimumQuantity; }
    public void setMinimumQuantity(int minimumQuantity) { this.minimumQuantity = minimumQuantity; }
}
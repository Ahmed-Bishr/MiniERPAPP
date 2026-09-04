package org.example.dto;

/**
 * Data container for a single sale item row.
 * Maps to the {@code sale_items} table. No SQL or business logic here.
 */
public class SaleItemDTO {

    private int saleItemId;
    private int saleId;
    private int productId;
    private int quantity;
    private double unitPrice;
    private double discount;

    public SaleItemDTO() {
        this(0, 0, 0, 0, 0, 0);
    }

    public SaleItemDTO(int saleItemId, int saleId, int productId, int quantity,
                       double unitPrice, double discount) {
        this.saleItemId = saleItemId;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    public int getSaleItemId() { return saleItemId; }
    public void setSaleItemId(int saleItemId) { this.saleItemId = saleItemId; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
}
package org.example.dto;

/**
 * Data container for a single product row.
 * Maps to the {@code products} table. No SQL or business logic here.
 */
public class ProductDTO {

    private int productId;
    private String name;
    private String category;
    private String barcode;
    private double purchasePrice;
    private double sellingPrice;

    public ProductDTO() {
        this(0, "", "", "", 0, 0);
    }

    public ProductDTO(int productId, String name, String category, String barcode,
                      double purchasePrice, double sellingPrice) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
}
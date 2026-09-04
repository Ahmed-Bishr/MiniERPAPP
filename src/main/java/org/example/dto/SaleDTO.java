package org.example.dto;

/**
 * Data container for a single sale row (joined with its customer and
 * employee names for display). Maps to the {@code sales} table.
 * No SQL or business logic here.
 */
public class SaleDTO {

    private int saleId;
    private String customerName;
    private String employeeName;
    private double total;
    private String paymentMethod;
    private String status;

    public SaleDTO() {
        this(0, "", "", 0, "", "");
    }

    public SaleDTO(int saleId, String customerName, String employeeName,
                   double total, String paymentMethod, String status) {
        this.saleId = saleId;
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
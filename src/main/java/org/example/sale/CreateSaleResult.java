package org.example.sale;

/**
 * Immutable holder returned by the sale transaction.
 * Carries the {@link CreateSaleStatus} plus, on success, the generated
 * sale id and the computed total. No UI code in this class.
 */
public class CreateSaleResult {

    private final CreateSaleStatus status;
    private final int saleId;
    private final double total;

    private CreateSaleResult(CreateSaleStatus status, int saleId, double total) {
        this.status = status;
        this.saleId = saleId;
        this.total = total;
    }

    public static CreateSaleResult success(int saleId, double total) {
        return new CreateSaleResult(CreateSaleStatus.SUCCESS, saleId, total);
    }

    public static CreateSaleResult failure(CreateSaleStatus status) {
        return new CreateSaleResult(status, 0, 0);
    }

    public CreateSaleStatus getStatus() { return status; }
    public int getSaleId() { return saleId; }
    public double getTotal() { return total; }
}
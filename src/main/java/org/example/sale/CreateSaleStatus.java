package org.example.sale;

/**
 * Outcome of an attempt to create a sale.
 * Lets the repository report success/failure without any UI code:
 * the menu layer translates each value into the matching dialog.
 */
public enum CreateSaleStatus {
    SUCCESS,
    PRODUCT_NOT_FOUND,
    INVENTORY_NOT_FOUND,
    NOT_ENOUGH_STOCK,
    COULD_NOT_CREATE_SALE
}
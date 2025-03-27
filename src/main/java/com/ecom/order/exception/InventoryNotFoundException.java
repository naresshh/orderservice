package com.ecom.order.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
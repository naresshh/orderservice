package com.ecom.order.exception;

public class InventoryServiceException extends RuntimeException {
    public InventoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
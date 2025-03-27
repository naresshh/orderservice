package com.ecom.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle InventoryNotFoundException (404)
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        logger.error("Inventory not found: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    // Handle InventoryServiceException (503)
    @ExceptionHandler(InventoryServiceException.class)
    public ResponseEntity<ErrorResponse> handleInventoryServiceException(InventoryServiceException ex) {
        logger.error("Inventory service error: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Handle Generic RuntimeException (500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Other Generic Exceptions (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Inner class to structure error responses
    static class ErrorResponse {
        private String message;
        private int statusCode;

        public ErrorResponse(String message, int statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}
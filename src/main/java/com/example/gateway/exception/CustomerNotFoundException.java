package com.example.gateway.exception;

import java.io.Serial;

public class CustomerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(Long id) {
        super("Customer not found with ID: " + id);
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long id, Throwable cause) {
        super("Customer not found with ID: " + id, cause);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
package com.example.gateway.exception;

import java.io.Serial;

public class EmployeeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(Long id) {
        super("Employee not found with ID: " + id);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Long id, Throwable cause) {
        super("Employee not found with ID: " + id, cause);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
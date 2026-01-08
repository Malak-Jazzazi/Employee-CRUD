package com.example.employee.shared.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Object id) {
        super(message + id);
    }
}

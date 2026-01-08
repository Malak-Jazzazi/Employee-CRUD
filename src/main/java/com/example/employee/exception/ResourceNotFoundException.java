package com.example.employee.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message ,UUID departmentId) {
        super(message + departmentId);
    }
}

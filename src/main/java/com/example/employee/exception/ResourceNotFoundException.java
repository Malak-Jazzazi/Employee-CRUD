package com.example.employee.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(UUID departmentId) {
        super("Department with Id not Found : " + departmentId);
    }
}

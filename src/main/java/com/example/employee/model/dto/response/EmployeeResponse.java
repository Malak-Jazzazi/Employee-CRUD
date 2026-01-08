package com.example.employee.model.dto.response;

import com.example.employee.model.entity.Department;
import com.example.employee.model.enums.EmployeeStatus;
import com.example.employee.model.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String jobTitle;
    private LocalDate hireDate;

    private EmploymentType employmentType;
    private EmployeeStatus status;

    private BigDecimal salary;

    private Boolean isDeleted;
    private DepartmentResponse department;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
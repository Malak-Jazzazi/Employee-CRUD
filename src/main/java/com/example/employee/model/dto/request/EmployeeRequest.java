package com.example.employee.model.dto.request;

import com.example.employee.model.enums.EmployeeStatus;
import com.example.employee.model.enums.EmploymentType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private LocalDate hireDate;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotNull(message = "Employee status is required")
    private EmployeeStatus status;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than 0")
    private BigDecimal salary;
}

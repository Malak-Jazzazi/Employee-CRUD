package com.example.employee.model.dto.request;

import com.example.employee.model.enums.EmployeeStatus;
import com.example.employee.model.enums.EmploymentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String phoneNumber;
    private LocalDate dateOfBirth;

    private String jobTitle;
    private LocalDate hireDate;

    private EmploymentType employmentType;
    private EmployeeStatus status;

    private BigDecimal salary;
}

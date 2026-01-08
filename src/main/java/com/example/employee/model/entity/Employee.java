package com.example.employee.model.entity;

import com.example.employee.model.enums.EmployeeStatus;
import com.example.employee.model.enums.EmploymentType;
import com.example.employee.shared.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class
Employee extends BaseEntity{

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String jobTitle;
    private LocalDate hireDate;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    private BigDecimal salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "department_id",
            nullable = false
    )
    private Department department;

}

package com.example.employee.repo;

import com.example.employee.model.entity.Employee;
import com.example.employee.shared.repo.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EmployeeRepository
        extends BaseRepository<Employee, UUID>,
        JpaSpecificationExecutor<Employee> {

    boolean existsByEmail(String email);
}

package com.example.employee.repo;

import com.example.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    Optional<Employee> findByIdAndIsDeletedFalse(UUID id);

    Page<Employee> findAllByIsDeletedFalse(Pageable pageable);
}

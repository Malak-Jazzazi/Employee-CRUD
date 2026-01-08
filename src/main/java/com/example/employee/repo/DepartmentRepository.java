package com.example.employee.repo;

import com.example.employee.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByIdAndIsDeletedFalse(UUID id);

    boolean existsByNameIgnoreCase(String name);
}

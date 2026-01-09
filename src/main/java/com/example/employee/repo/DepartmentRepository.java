package com.example.employee.repo;

import com.example.employee.model.entity.Department;
import com.example.employee.shared.repo.BaseRepository;

import java.util.UUID;

public interface DepartmentRepository
        extends BaseRepository<Department, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
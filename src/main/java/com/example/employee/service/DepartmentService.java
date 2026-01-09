package com.example.employee.service;

import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.model.dto.request.DepartmentRequest;
import com.example.employee.model.dto.response.DepartmentResponse;
import com.example.employee.model.entity.Department;
import com.example.employee.repo.DepartmentRepository;
import com.example.employee.shared.mapper.BaseMapper;
import com.example.employee.shared.repo.BaseRepository;
import com.example.employee.shared.service.BaseCrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepartmentService
        extends BaseCrudService<Department, UUID, DepartmentRequest, DepartmentResponse> {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public DepartmentService(DepartmentRepository repository,
                             DepartmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected BaseRepository<Department, UUID> getRepository() {
        return repository;
    }

    @Override
    protected BaseMapper<Department, DepartmentRequest, DepartmentResponse> getMapper() {
        return mapper;
    }

    @Override
    public DepartmentResponse create(DepartmentRequest request) {

        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Department already exists: " + request.getName());
        }

        Department department = mapper.toEntity(request);
        department.setIsDeleted(false);

        return mapper.toResponse(repository.save(department));
    }
}

package com.example.employee.service;

import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import com.example.employee.repo.DepartmentRepository;
import com.example.employee.repo.EmployeeRepository;
import com.example.employee.shared.exception.EmailAlreadyExistsException;
import com.example.employee.shared.exception.ResourceNotFoundException;
import com.example.employee.shared.mapper.BaseMapper;
import com.example.employee.shared.service.BaseCrudService;
import com.example.employee.specification.EmployeeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService
        extends BaseCrudService<Employee, UUID, EmployeeRequest, EmployeeResponse> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    protected JpaRepository<Employee, UUID> getRepository() {
        return employeeRepository;
    }

    @Override
    protected BaseMapper<Employee, EmployeeRequest, EmployeeResponse> getMapper() {
        return employeeMapper;
    }

    @Override
    protected Optional<Employee> findActiveById(UUID id) {
        return employeeRepository.findByIdAndIsDeletedFalse(id);
    }

    @Override
    protected void softDelete(Employee entity) {
        entity.setIsDeleted(true);
    }

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        log.info("Creating employee with email {}", request.getEmail());

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found",
                        request.getDepartmentId()
                ));

        Employee employee = employeeMapper.toEntity(request);
        employee.setIsDeleted(false);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public Page<EmployeeResponse> searchEmployees(String search, Pageable pageable) {

        Specification<Employee> spec = Specification
                .where(EmployeeSpecification.isNotDeleted())
                .and(EmployeeSpecification.globalSearch(search));

        return employeeRepository
                .findAll(spec, pageable)
                .map(employeeMapper::toResponse);
    }
}

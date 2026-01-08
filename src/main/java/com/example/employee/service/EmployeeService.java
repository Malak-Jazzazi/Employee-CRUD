package com.example.employee.service;

import com.example.employee.exception.EmailAlreadyExistsException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import com.example.employee.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
            throw new EmailAlreadyExistsException(employeeRequest.getEmail());
        }
        Employee employee = employeeMapper.employeeRequestToEmployeeEntity(employeeRequest);
        employee.setIsDeleted(false);
        employeeRepository.save(employee);
        return employeeMapper.employeeEntityToEmployeeResponse(employee);
    }
}

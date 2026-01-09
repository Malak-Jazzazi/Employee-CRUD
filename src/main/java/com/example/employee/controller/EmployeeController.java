package com.example.employee.controller;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.shared.controller.BaseController;
import com.example.employee.shared.service.BaseCrudService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("employee")
public class EmployeeController
        extends BaseController<UUID, EmployeeRequest, EmployeeResponse> {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    protected BaseCrudService<?, UUID, EmployeeRequest, EmployeeResponse> getService() {
        return employeeService;
    }


    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public Page<EmployeeResponse> searchEmployees(
            @RequestParam(required = false) String search,
            Pageable pageable) {

        return employeeService.searchEmployees(search, pageable);
    }
}

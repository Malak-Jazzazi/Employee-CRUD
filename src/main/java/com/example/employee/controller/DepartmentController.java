package com.example.employee.controller;

import com.example.employee.model.dto.request.DepartmentRequest;
import com.example.employee.model.dto.response.DepartmentResponse;
import com.example.employee.service.DepartmentService;
import com.example.employee.shared.controller.BaseController;
import com.example.employee.shared.service.BaseCrudService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("department")
@RequiredArgsConstructor
public class DepartmentController
        extends BaseController<UUID, DepartmentRequest, DepartmentResponse> {

    private final DepartmentService service;

    @Override
    protected BaseCrudService<?, UUID, DepartmentRequest, DepartmentResponse> getService() {
        return service;
    }
}

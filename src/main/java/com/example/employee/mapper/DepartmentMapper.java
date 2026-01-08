package com.example.employee.mapper;

import com.example.employee.model.dto.request.DepartmentRequest;
import com.example.employee.model.dto.response.DepartmentResponse;
import com.example.employee.model.entity.Department;
import com.example.employee.shared.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DepartmentMapper
        extends BaseMapper<Department, DepartmentRequest, DepartmentResponse> {
}
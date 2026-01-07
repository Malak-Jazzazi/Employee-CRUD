package com.example.employee.mapper;

import com.example.employee.model.dto.request.EmployeeRequest;
import com.example.employee.model.dto.response.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );

     Employee employeeRequestToEmployeeEntity(EmployeeRequest employeeRequest);

     EmployeeResponse employeeEntityToEmployeeResponse(Employee employee);
}

package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto updateEmployee(Integer id, EmployeeDto employee);

    public String deleteEmployee(Integer id);

    public EmployeeDto addEmployee(EmployeeDto employeeDto);

    public List<EmployeeDto> getAllEmployees();
}

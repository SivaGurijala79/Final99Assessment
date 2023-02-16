package com.final99.EmployeeAccess.testData;

import com.final99.EmployeeAccess.dto.EmployeeDto;
import com.final99.EmployeeAccess.dto.ModuleDto;
import com.final99.EmployeeAccess.entity.Employee;
import com.final99.EmployeeAccess.entity.Module;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TestData {

    public static Employee prepareEmployee(String empName, List<Module> modules) {
        Employee employee = new Employee();
        employee.setName(empName);
        if (Objects.nonNull(modules)) {
            employee.setModules(new HashSet<>(modules));
        }
        return employee;
    }

    public static Module prepareModule(String moduleName, List<Employee> employees) {
        Module module = new Module();
        module.setName(moduleName);
        if (Objects.nonNull(employees)) {
            module.setEmployees(new HashSet<>(employees));
        }
        return module;
    }

    public static EmployeeDto prepareEmployeeDto(String empName, List<String> modules) {
        EmployeeDto employee = new EmployeeDto();
        employee.setName(empName);
        if (Objects.nonNull(modules)) {
            employee.setModules(new HashSet<>(modules));
        }
        return employee;
    }

    public static ModuleDto prepareModuleDto(String moduleName, List<String> employees) {
        ModuleDto module = new ModuleDto();
        module.setName(moduleName);
        if (Objects.nonNull(employees)) {
            module.setEmployees(new HashSet<>(employees));
        }
        return module;
    }
}

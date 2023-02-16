package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.EmployeeDto;
import com.final99.EmployeeAccess.entity.Employee;
import com.final99.EmployeeAccess.entity.Module;
import com.final99.EmployeeAccess.exception.ItemNotFoundException;
import com.final99.EmployeeAccess.repository.EmployeeRepository;
import com.final99.EmployeeAccess.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Transactional
    @Override
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employee) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee emp = employeeOpt.get();
            emp.getModules().clear();
            mapDtoToEntity(employee, emp);
            return mapEntityToDto(employeeRepository.save(emp));
        }
        logger.error("Employee with Id {} is not found to update", id);
        throw new ItemNotFoundException("Employee with Id " + id + " is not found to update");
    }

    @Override
    public String deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employee.get().removeModules();
            employeeRepository.deleteById(employee.get().getId());
            return "Employee with id: " + id + " deleted successfully!";
        }
        logger.error("Employee with Id {} not found to delete", id);
        throw new ItemNotFoundException("Employee with Id " + id + " not found to delete");
    }

    @Transactional
    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        mapDtoToEntity(employeeDto, employee);
        return mapEntityToDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(employee -> {
            EmployeeDto employeeDto = mapEntityToDto(employee);
            employeeDtos.add(employeeDto);
        });
        return employeeDtos;
    }

    private void mapDtoToEntity(EmployeeDto employeeDto, Employee employee) {
        employee.setName(employeeDto.getName());
        if (Objects.isNull(employee.getModules())) {
            employee.setModules(new HashSet<>());
        }
        employeeDto.getModules().forEach(courseName -> {
            Module module = moduleRepository.findByName(courseName);
            if (Objects.isNull(module)) {
                module = new Module();
                module.setEmployees(new HashSet<>());
            }
            module.setName(courseName);
            employee.addModule(module);
        });
    }

    private EmployeeDto mapEntityToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setName(employee.getName());
        dto.setId(employee.getId());
        dto.setModules(employee.getModules().stream().map(Module::getName).collect(Collectors.toSet()));
        return dto;
    }
}

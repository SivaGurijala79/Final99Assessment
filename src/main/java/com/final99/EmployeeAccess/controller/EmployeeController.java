package com.final99.EmployeeAccess.controller;

import com.final99.EmployeeAccess.dto.EmployeeDto;
import com.final99.EmployeeAccess.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        logger.debug("Processing getAllEmployees request");
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        logger.debug("processing addEmployee request with body {}", employeeDto);
        EmployeeDto employee = employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "id") Integer id,
                                                      @RequestBody EmployeeDto employee) {
        logger.debug("processing updateemployee request for employee with id {} with body {}", id, employee);
        EmployeeDto emp = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "id") Integer employeeId) {
        logger.debug("processing deleteemployee with id {}", employeeId);
        String message = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

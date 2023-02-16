package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.EmployeeDto;
import com.final99.EmployeeAccess.entity.Employee;
import com.final99.EmployeeAccess.entity.Module;
import com.final99.EmployeeAccess.exception.ItemNotFoundException;
import com.final99.EmployeeAccess.repository.EmployeeRepository;
import com.final99.EmployeeAccess.repository.ModuleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.final99.EmployeeAccess.testData.TestData.prepareEmployee;
import static com.final99.EmployeeAccess.testData.TestData.prepareEmployeeDto;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    Employee employee = prepareEmployee("Employee1", Collections.emptyList());
    EmployeeDto employeeDto = prepareEmployeeDto("Employee1", Arrays.asList("Module1", "Module2"));
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ModuleRepository moduleRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Before
    public void init() {
        when(employeeRepository.findById(1001)).thenReturn(Optional.of(employee));
    }

    @Test
    public void updateEmployee() {

        Module m1 = new Module();
        m1.setName("Module1");
        Module m2 = new Module();
        m2.setName("Module2");
        Employee updatedEmployee = prepareEmployee("Employee1", Arrays.asList(m1, m2));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        EmployeeDto response = employeeService.updateEmployee(1001, employeeDto);
        assertEquals("Employee1", response.getName());
        assertEquals(2, response.getModules().size());
        assertTrue(response.getModules().stream().anyMatch(m -> m.equals("Module1")));
        assertTrue(response.getModules().stream().anyMatch(m -> m.equals("Module2")));
    }

    @Test(expected = ItemNotFoundException.class)
    public void updateEmployee_NotFound() {
        when(employeeRepository.findById(1001)).thenReturn(Optional.empty());
        employeeService.updateEmployee(1001, employeeDto);
        fail("ItemNotFoundException expected");
    }


    @Test
    public void deleteEmployee() {
        String response = employeeService.deleteEmployee(1001);
        assertEquals("Employee with id: 1001 deleted successfully!", response);
    }

    @Test(expected = ItemNotFoundException.class)
    public void deleteEmployee_NotFound() {
        when(employeeRepository.findById(1001)).thenReturn(Optional.empty());
        employeeService.deleteEmployee(1001);
        fail("ItemNotFoundException expected");
    }

    @Test
    public void addEmployee() {
        when(employeeRepository.save(any())).thenReturn(employee);
        EmployeeDto response = employeeService.addEmployee(employeeDto);
        assertEquals(employee.getName(), response.getName());
        assertEquals(employee.getModules().size(), response.getModules().size());
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee dbObj = captor.getValue();
        assertEquals("Employee1", dbObj.getName());
        assertTrue(dbObj.getModules().stream().anyMatch(m -> m.getName().equals("Module1")));
        assertTrue(dbObj.getModules().stream().anyMatch(m -> m.getName().equals("Module2")));
    }

    @Test
    public void getAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        assertEquals(1, employees.size());
    }
}
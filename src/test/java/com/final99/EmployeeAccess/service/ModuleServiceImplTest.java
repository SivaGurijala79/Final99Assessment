package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.ModuleDto;
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

import static com.final99.EmployeeAccess.testData.TestData.prepareModule;
import static com.final99.EmployeeAccess.testData.TestData.prepareModuleDto;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModuleServiceImplTest {

    Module module = prepareModule("Module1", Collections.emptyList());
    ModuleDto moduleDto = prepareModuleDto("Module1", Arrays.asList("Employee1", "Employee2"));
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ModuleRepository moduleRepository;
    @InjectMocks
    private ModuleServiceImpl moduleService;

    @Before
    public void init() {
        when(moduleRepository.findById(1001)).thenReturn(Optional.of(module));
    }

    @Test
    public void addModule() {
        when(moduleRepository.save(any())).thenReturn(module);
        ModuleDto response = moduleService.addModule(moduleDto);
        assertEquals(module.getName(), response.getName());
        assertEquals(module.getEmployees().size(), response.getEmployees().size());
        ArgumentCaptor<Module> captor = ArgumentCaptor.forClass(Module.class);
        verify(moduleRepository).save(captor.capture());
        Module dbObj = captor.getValue();
        assertEquals("Module1", dbObj.getName());
        assertTrue(dbObj.getEmployees().stream().anyMatch(m -> m.getName().equals("Employee1")));
        assertTrue(dbObj.getEmployees().stream().anyMatch(m -> m.getName().equals("Employee2")));
    }

    @Test
    public void getAllModules() {

        when(moduleRepository.findAll()).thenReturn(Collections.singletonList(module));
        List<ModuleDto> modules = moduleService.getAllModules();
        assertEquals(1, modules.size());
    }

    @Test
    public void updateModule() {

        Employee e1 = new Employee();
        e1.setName("Employee1");
        Employee e2 = new Employee();
        e2.setName("Employee2");
        Module updatedModule = prepareModule("Module1", Arrays.asList(e1, e2));
        when(moduleRepository.save(any(Module.class))).thenReturn(updatedModule);
        ModuleDto response = moduleService.updateModule(1001, moduleDto);
        assertEquals("Module1", response.getName());
        assertEquals(2, response.getEmployees().size());
        assertTrue(response.getEmployees().stream().anyMatch(m -> m.equals("Employee1")));
        assertTrue(response.getEmployees().stream().anyMatch(m -> m.equals("Employee2")));
    }

    @Test(expected = ItemNotFoundException.class)
    public void updateModule_NotFound() {
        when(moduleRepository.findById(1001)).thenReturn(Optional.empty());
        moduleService.updateModule(1001, moduleDto);
        fail("ItemNotFoundException expected");
    }

    @Test
    public void deleteModule() {
        String response = moduleService.deleteModule(1001);
        assertEquals("Module with id: 1001 deleted successfully!", response);
    }

    @Test(expected = ItemNotFoundException.class)
    public void deleteModule_NotFound() {
        when(moduleRepository.findById(1001)).thenReturn(Optional.empty());
        moduleService.deleteModule(1001);
        fail("ItemNotFoundException expected");
    }

}
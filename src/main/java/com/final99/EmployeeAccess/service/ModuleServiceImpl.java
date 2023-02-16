package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.ModuleDto;
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
public class ModuleServiceImpl implements ModuleService {
    Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Transactional
    @Override
    public ModuleDto addModule(ModuleDto moduleDto) {
        Module module = new Module();
        mapDtoToEntity(moduleDto, module);
        Module savedModule = moduleRepository.save(module);
        return mapEntityToDto(savedModule);
    }

    @Override
    public List<ModuleDto> getAllModules() {
        List<ModuleDto> moduleDtos = new ArrayList<>();
        List<Module> modules = moduleRepository.findAll();
        modules.forEach(module -> {
            ModuleDto moduleDto = mapEntityToDto(module);
            moduleDtos.add(moduleDto);
        });
        return moduleDtos;
    }

    @Transactional
    @Override
    public ModuleDto updateModule(Integer id, ModuleDto moduleDto) {
        Optional<Module> moduleOpt = moduleRepository.findById(id);
        if (moduleOpt.isPresent()) {
            Module module = moduleOpt.get();
            module.getEmployees().clear();
            mapDtoToEntity(moduleDto, module);
            return mapEntityToDto(moduleRepository.save(module));
        }
        logger.error("Module with id {} is not found", id);
        throw new ItemNotFoundException("Module with id " + id + " is ot found to update");
    }

    @Transactional
    @Override
    public String deleteModule(Integer id) {
        Optional<Module> module = moduleRepository.findById(id);
        // Remove the related employees from module entity.
        if (module.isPresent()) {
            module.get().removeEmployees();
            moduleRepository.deleteById(module.get().getId());
            return "Module with id: " + id + " deleted successfully!";
        }
        logger.error("Module with id {} is not found", id);
        throw new ItemNotFoundException("Module with id " + id + " is not found to delete");
    }

    private void mapDtoToEntity(ModuleDto moduleDto, Module module) {
        module.setName(moduleDto.getName());
        if (Objects.isNull(module.getEmployees())) {
            module.setEmployees(new HashSet<>());
        }
        moduleDto.getEmployees().forEach(employeeName -> {
            Employee employee = employeeRepository.findByName(employeeName);
            if (Objects.isNull(employee)) {
                employee = new Employee();
                employee.setModules(new HashSet<>());
            }
            employee.setName(employeeName);
            employee.addModule(module);
        });
    }

    private ModuleDto mapEntityToDto(Module module) {
        ModuleDto responseDto = new ModuleDto();
        responseDto.setName(module.getName());
        responseDto.setId(module.getId());
        responseDto.setEmployees(module.getEmployees().stream().map(Employee::getName).collect(Collectors.toSet()));
        return responseDto;
    }
}

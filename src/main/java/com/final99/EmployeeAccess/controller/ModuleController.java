package com.final99.EmployeeAccess.controller;

import com.final99.EmployeeAccess.dto.ModuleDto;
import com.final99.EmployeeAccess.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModuleController {
    Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        logger.debug("processing getAllModules");
        List<ModuleDto> modules = moduleService.getAllModules();
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @PostMapping("/module")
    public ResponseEntity<ModuleDto> saveModule(@RequestBody ModuleDto moduleDto) {
        logger.debug("processing saveModule with body {}", moduleDto);
        ModuleDto module = moduleService.addModule(moduleDto);
        return new ResponseEntity<>(module, HttpStatus.CREATED);
    }

    @PutMapping("/module/{id}")
    public ResponseEntity<ModuleDto> updateModule(@PathVariable(name = "id") Integer id,
                                                  @RequestBody ModuleDto module) {
        logger.debug("processing updateModule for module id {} with body {}", id, module);
        ModuleDto updatedModule = moduleService.updateModule(id, module);
        return new ResponseEntity<>(updatedModule, HttpStatus.CREATED);
    }

    @DeleteMapping("/module/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable(name = "id") Integer id) {
        logger.debug("processing deleteModule for module id {} ", id);
        String message = moduleService.deleteModule(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}

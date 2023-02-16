package com.final99.EmployeeAccess.service;

import com.final99.EmployeeAccess.dto.ModuleDto;

import java.util.List;

public interface ModuleService {

    public ModuleDto updateModule(Integer id, ModuleDto module);

    public String deleteModule(Integer id);

    public ModuleDto addModule(ModuleDto moduleDto);

    public List<ModuleDto> getAllModules();
}

package com.final99.EmployeeAccess.repository;

import com.final99.EmployeeAccess.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    public Module findByName(String moduleName);
}

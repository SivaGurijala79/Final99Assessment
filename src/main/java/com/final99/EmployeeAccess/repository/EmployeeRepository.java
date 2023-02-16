package com.final99.EmployeeAccess.repository;

import com.final99.EmployeeAccess.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String employeeName);

}

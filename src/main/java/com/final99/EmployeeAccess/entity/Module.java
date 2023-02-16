package com.final99.EmployeeAccess.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "MODULE")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_sequence")
    @SequenceGenerator(name = "module_sequence", sequenceName = "module_sequence")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "modules", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public void removeEmployee(Employee employee) {
        this.getEmployees().remove(employee);
        employee.getModules().remove(this);
    }

    public void removeEmployees() {
        for (Employee employee : new HashSet<>(employees)) {
            removeEmployee(employee);
        }
    }
}

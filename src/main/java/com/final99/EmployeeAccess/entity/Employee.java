package com.final99.EmployeeAccess.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @SequenceGenerator(initialValue = 1000, name = "employee_sequence", sequenceName = "employee_sequence")
    private Integer id;

    @Column(name = "name")
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_modules",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> modules;

    public void addModule(Module module) {
        this.modules.add(module);
        module.getEmployees().add(this);
    }

    public void removeModule(Module module) {
        this.getModules().remove(module);
        module.getEmployees().remove(this);
    }

    public void removeModules() {
        for (Module module : new HashSet<>(modules)) {
            removeModule(module);
        }
    }
}

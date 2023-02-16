package com.final99.EmployeeAccess.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ModuleDto {
    Set<String> employees = new HashSet<>();
    private Integer id;
    private String name;
}

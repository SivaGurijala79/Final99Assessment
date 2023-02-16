package com.final99.EmployeeAccess.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployeeDto {

    private Integer id;
    private String name;
    private Set<String> modules = new HashSet<>();
}

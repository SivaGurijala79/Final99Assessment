package com.final99.EmployeeAccess.exception;

public class ItemNotFoundException extends RuntimeException {
    private String message;

    public ItemNotFoundException(String message) {
        super(message);
    }
}

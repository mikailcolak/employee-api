package com.employee.api.controller.exception;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(Long id) {
        super(String.format("Employee with the id %d could not be found", id));
    }

}

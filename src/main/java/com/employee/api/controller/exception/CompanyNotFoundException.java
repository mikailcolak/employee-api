package com.employee.api.controller.exception;

public class CompanyNotFoundException extends Exception {

    public CompanyNotFoundException(Long id) {
        super(String.format("Company with the id %d could not be found", id));
    }

}

package com.employee.api.controller.advice;

import com.employee.api.controller.exception.CompanyNotFoundException;
import com.employee.api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class CompanyNotFoundAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Object companyNotFoundHandler(CompanyNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
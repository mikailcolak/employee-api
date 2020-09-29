package com.employee.api.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.employee.api.controller.CompanyController;
import com.employee.api.controller.EmployeeController;
import com.employee.api.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        try {
            return EntityModel.of(
                employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(CompanyController.class).one(employee.getCompanyId())).withRel("company"),
                linkTo(methodOn(EmployeeController.class).all(Pageable.unpaged())).withRel("employees")
            );
        } catch (Exception ex) {
            log.error("An unexpected error occurred while assembling Employee model", ex);
            return EntityModel.of(employee);
        }
    }
}

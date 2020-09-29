package com.employee.api.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.employee.api.controller.CompanyController;
import com.employee.api.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CompanyModelAssembler implements RepresentationModelAssembler<Company, EntityModel<Company>> {

    @Override
    public EntityModel<Company> toModel(Company company) {
        try {
            return EntityModel.of(
                company,
                linkTo(methodOn(CompanyController.class).one(company.getId())).withSelfRel(),
                linkTo(methodOn(CompanyController.class).all(Pageable.unpaged())).withRel("companies")
            );
        } catch (Exception ex) {
            log.error("An unexpected error occurred while assembling Company model", ex);
            return EntityModel.of(company);
        }
    }
}

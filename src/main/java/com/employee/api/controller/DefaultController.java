package com.employee.api.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public RepresentationModel<?> index() {

        RepresentationModel<?> rootModel = new RepresentationModel<>();
        rootModel.add(linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
        rootModel.add(linkTo(methodOn(CompanyController.class).all()).withRel("companies"));

        return rootModel;
    }
}

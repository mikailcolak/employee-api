package com.employee.api.controller;

import com.employee.api.controller.assembler.CompanyModelAssembler;
import com.employee.api.controller.exception.CompanyNotFoundException;
import com.employee.api.model.Company;
import com.employee.api.repository.CompanyRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository repository;
    private final CompanyModelAssembler assembler;

    public CompanyController(
        CompanyRepository repository,
        CompanyModelAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Company>> all() {

        var companies = repository
            .findAll()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(companies, linkTo(methodOn(CompanyController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<?> newCompany(@RequestBody Company newCompany) {

        var entityModel = assembler.toModel(repository.save(newCompany));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    // Single item

    @GetMapping("/{id}")
    public EntityModel<Company> one(@PathVariable Long id) throws CompanyNotFoundException {

        var company = repository
            .findById(id)
            .orElseThrow(() -> new CompanyNotFoundException(id));

        return assembler.toModel(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceCompany(@RequestBody Company newCompany, @PathVariable Long id) {

        var updatedCompany = repository.findById(id)
            .map(company -> {
                company.setName(newCompany.getName());
                return repository.save(company);
            })
            .orElseGet(() -> {
                newCompany.setId(id);
                return repository.save(newCompany);
            });

        var entityModel = assembler.toModel(updatedCompany);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

package com.employee.api.controller;

import com.employee.api.controller.assembler.EmployeeModelAssembler;
import com.employee.api.controller.exception.EmployeeNotFoundException;
import com.employee.api.model.Employee;
import com.employee.api.repository.EmployeeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(
        EmployeeRepository repository,
        EmployeeModelAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Employee>> all(Pageable pageable) {

        var employees = repository
            .findAll(pageable)
            .stream()
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return PagedModel.of(employees, linkTo(methodOn(EmployeeController.class).all(pageable)).withSelfRel());
    }

    @GetMapping("/by-company-id/{id}")
    public CollectionModel<EntityModel<Employee>> allByCompanyId(@PathVariable Long id) {

        var employees = repository
            .findAllByCompanyId(id)
            .stream()
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all(Pageable.unpaged())).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

        var entityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    // Single item

    @GetMapping("/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) throws EmployeeNotFoundException {

        var employee = repository
            .findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        var updatedEmployee = repository.findById(id)
            .map(employee -> {
                employee.setName(newEmployee.getName());
                employee.setSurname(newEmployee.getSurname());
                employee.setEmail(newEmployee.getEmail());
                employee.setAddress(newEmployee.getAddress());
                employee.setSalary(newEmployee.getSalary());
                employee.setCompanyId(newEmployee.getCompanyId());
                return repository.save(employee);
            })
            .orElseGet(() -> {
                newEmployee.setId(id);
                return repository.save(newEmployee);
            });

        var entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

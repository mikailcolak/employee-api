package com.employee.api.model;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="EMPLOYEE_SEQUENCE")
    @SequenceGenerator(name="EMPLOYEE_SEQUENCE", sequenceName="EMPLOYEE_SEQUENCE_ID", initialValue=1, allocationSize=1)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private float salary;
    private Long companyId;


    public Employee() {}

    public Employee(Long id, String name, String surname, String email, String address, float salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Employee setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Employee setAddress(String address) {
        this.address = address;
        return this;
    }

    public float getSalary() {
        return salary;
    }

    public Employee setSalary(float salary) {
        this.salary = salary;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Employee setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 31 * hash + (id == null ? 0 : id.hashCode());
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (surname == null ? 0 : surname.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        hash = 31 * hash + (address == null ? 0 : address.hashCode());
        hash = 31 * hash + (int)salary;
        hash = 31 * hash + (companyId == null ? 0 : companyId.hashCode());

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() {
        return new Employee(
            id,
            name,
            surname,
            email,
            address,
            salary,
            companyId
        );
    }

    @Override
    public String toString() {
        return String.format(
            "[Employee id=%d name=\"%s\" surname=\"%s\" email=\"%s\" address=\"%s\" salary=%.4f companyId=%d]",
            id,
            name,
            surname,
            email,
            address,
            salary,
            companyId
        );
    }
}

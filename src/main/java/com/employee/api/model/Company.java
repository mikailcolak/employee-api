package com.employee.api.model;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="COMPANY_SEQUENCE")
    @SequenceGenerator(name="COMPANY_SEQUENCE", sequenceName="COMPANY_SEQUENCE_ID", initialValue=1, allocationSize=1)
    private Long id;
    private String name;

    public Company() {}

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Company setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 31 * hash + (id == null ? 0 : id.hashCode());
        hash = 31 * hash + (name == null ? 0 : name.hashCode());

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() {
        return new Company(
            id,
            name
        );
    }

    @Override
    public String toString() {
        return String.format(
            "[Company id=%d name=\"%s\"]",
            id,
            name
        );
    }
}

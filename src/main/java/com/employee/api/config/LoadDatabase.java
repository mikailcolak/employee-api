package com.employee.api.config;

import com.employee.api.model.Company;
import com.employee.api.model.Employee;
import com.employee.api.repository.CompanyRepository;
import com.employee.api.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        return args -> {

            var company = companyRepository.save(new Company().setName("EBF"));

            employeeRepository.save(
                new Employee()
                    .setCompanyId(company.getId())
                    .setName("TEST1")
                    .setSurname("TEST1_SURNAME")
                    .setEmail("test1@employeeapi.com")
                    .setAddress("ADDRESS1")
                    .setSalary(4567.0F)
            );

            employeeRepository.save(
                new Employee()
                    .setCompanyId(company.getId())
                    .setName("TEST2")
                    .setSurname("TEST2_SURNAME")
                    .setEmail("test2@employeeapi.com")
                    .setAddress("ADDRESS2")
                    .setSalary(4598.0F)
            );

        };
    }
}

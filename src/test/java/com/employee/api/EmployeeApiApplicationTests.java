package com.employee.api;

import com.employee.api.controller.CompanyController;
import com.employee.api.controller.DefaultController;
import com.employee.api.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeApiApplicationTests {

	@Autowired
	private DefaultController defaultController;

	@Autowired
	private CompanyController companyController;

	@Autowired
	private EmployeeController employeeController;

	@Test
	void contextLoads() {
		assertThat(defaultController).isNotNull();
		assertThat(companyController).isNotNull();
		assertThat(employeeController).isNotNull();
	}

}

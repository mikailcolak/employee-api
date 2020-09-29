package com.employee.api.controller;

import com.employee.api.model.Employee;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String urlTpl = "http://localhost:%d/employees/%s";

    @Test
    public void all() throws Exception {

        var request = RequestEntity
            .get(uri(""))
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = new ParameterizedTypeReference<CollectionModel<EntityModel<Employee>>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLinks().hasSize(1)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().size()).isGreaterThan(0);

    }

    @Test
    public void allByCompanyId() throws Exception {

        var request = RequestEntity
            .get(uri("by-company-id/1?page=0&size=1"))
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = new ParameterizedTypeReference<CollectionModel<EntityModel<Employee>>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLinks().hasSize(1)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().size()).isGreaterThan(0);

    }

    @Test
    public void newEmployee() throws Exception {

        var request = RequestEntity
            .post(uri(""))
            .accept(MediaType.APPLICATION_JSON)
            .body(
                new Employee()
                    .setCompanyId(1L)
                    .setName("TEST3")
                    .setSurname("TEST3_SURNAME")
                    .setEmail("test3@employeeapi.com")
                    .setAddress("ADDRESS3")
                    .setSalary(5000.0F)
            );

        var responseType = new ParameterizedTypeReference<EntityModel<Employee>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getLinks().hasSize(3)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().getName()).isEqualTo("TEST3");

    }

    @Test
    public void one() throws Exception {

        var request = RequestEntity
            .get(uri("2")) // get TEST2 with id 2
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = new ParameterizedTypeReference<EntityModel<Employee>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLinks().hasSize(3)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().getName()).isEqualTo("TEST2");

    }

    @Test
    public void replaceEmployee() throws Exception {

        var request = RequestEntity
            .put(uri("2")) // update TEST2 with id 2
            .accept(MediaType.APPLICATION_JSON)
            .body(
                new Employee()
                    .setCompanyId(1L)
                    .setName("TEST2_UPDATED")
                    .setSurname("TEST2_UPDATED_SURNAME")
                    .setEmail("test2_updated@employeeapi.com")
                    .setAddress("ADDRESS2_UPDATED")
                    .setSalary(4568.0F)
            );

        var responseType = new ParameterizedTypeReference<EntityModel<Employee>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getLinks().hasSize(3)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();

        var employee = response.getBody().getContent();
        assertThat(employee.getCompanyId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("TEST2_UPDATED");
        assertThat(employee.getSurname()).isEqualTo("TEST2_UPDATED_SURNAME");
        assertThat(employee.getEmail()).isEqualTo("test2_updated@employeeapi.com");
        assertThat(employee.getAddress()).isEqualTo("ADDRESS2_UPDATED");
        assertThat(employee.getSalary()).isEqualTo(4568.0F);

    }

    @Test
    public void deleteEmployee() throws Exception {

        var request = RequestEntity
            .delete(uri("1")) // delete TEST1 with id 1
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = String.class;
        var response = restTemplate.exchange(request, responseType);

        var checkRequest = RequestEntity
            .get(uri("1")) // get TEST with id 1
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var checkResponse = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(checkResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    // Utilities
    private String url(String route) {
        return String.format(urlTpl, port, route);
    }

    private URI uri(String route) throws URISyntaxException {
        return new URI(url(route));
    }
}


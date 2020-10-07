package com.employee.api.controller;

import com.employee.api.model.Company;
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
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String urlTpl = "http://localhost:%d/companies/%s";

    @Test
    public void all() throws Exception {

        var request = RequestEntity
            .get(uri(""))
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = new ParameterizedTypeReference<CollectionModel<EntityModel<Company>>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLinks().hasSize(1)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().size()).isEqualTo(1);

    }

    @Test
    public void newCompany() throws Exception {

        var request = RequestEntity
            .post(uri(""))
            .accept(MediaType.APPLICATION_JSON)
            .body(new Company().setName("Lidyum"));

        var responseType = new ParameterizedTypeReference<EntityModel<Company>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getLinks().hasSize(2)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().getName()).isEqualTo("Lidyum");

    }

    @Test
    public void one() throws Exception {

        var request = RequestEntity
            .get(uri("1")) // get EBF with id 1
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = new ParameterizedTypeReference<EntityModel<Company>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLinks().hasSize(2)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().getName()).isEqualTo("EBF");

    }

    @Test
    public void replaceCompany() throws Exception {

        var request = RequestEntity
            .put(uri("1")) // update EBF with id 1
            .accept(MediaType.APPLICATION_JSON)
            .body(new Company(1L, "EBF_UPDATED"));

        var responseType = new ParameterizedTypeReference<EntityModel<Company>>() {};
        var response = restTemplate.exchange(request, responseType);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getLinks().hasSize(2)).isTrue();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent().getName()).isEqualTo("EBF_UPDATED");

    }

    @Test
    public void deleteCompany() throws Exception {

        var request = RequestEntity
            .delete(uri("1")) // delete EBF with id 1
            .accept(MediaType.APPLICATION_JSON)
            .build();

        var responseType = String.class;
        var response = restTemplate.exchange(request, responseType);

        var checkRequest = RequestEntity
            .get(uri("1")) // get EBF with id 1
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


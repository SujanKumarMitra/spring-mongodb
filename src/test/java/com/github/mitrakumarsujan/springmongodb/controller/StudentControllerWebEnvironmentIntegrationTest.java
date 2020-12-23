package com.github.mitrakumarsujan.springmongodb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class StudentControllerWebEnvironmentIntegrationTest {

    private TestRestTemplate restTemplate;
    private String rootUrl;

    @Autowired
    public StudentControllerWebEnvironmentIntegrationTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.rootUrl = restTemplate.getRootUri();
    }

    @Test
    void testGetStudents() {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students"),
                GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testGetStudentByValidRoll() {
        Long validRoll = 1L;

        Map<String,Object> request = new HashMap<>();
        request.put("roll", validRoll);
        request.put("name","Test Name");

        restTemplate.postForEntity(
                rootUrl.concat("/students"),
                new HttpEntity<>(request),
                Map.class
        );

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students/{validRoll}"),
                GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                validRoll
        );

        assertEquals(OK, responseEntity.getStatusCode());
        deleteCreatedStudent(validRoll);
    }

    @Test
    void testGetStudentByInvalidRoll() {
        Long invalidRoll = 1L;
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students/{invalidRoll}"),
                GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                invalidRoll
        );

        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void testPostStudentWithValidBody() {
        Long roll = 1L;

        Map<String, Object> request = new HashMap<>();
        request.put("name", "Sujan");
        request.put("roll", 1L);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students"),
                POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );

        assertEquals(CREATED, responseEntity.getStatusCode());
        deleteCreatedStudent(roll);
    }

    @Test
    void testPostStudentWithNullRoll() {
        Long roll = 1L;

        Map<String, Object> request = new HashMap<>();
        request.put("roll", null);
        request.put("name", "Sujan");

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students"),
                POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testPostStudentWithNullName() {
        Long roll = 1L;

        Map<String, Object> request = new HashMap<>();
        request.put("roll", 1L);
        request.put("name", null);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students"),
                POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteStudentWithValidRoll() {
        Long roll = 1L;

        Map<String, Object> request = new HashMap<>();
        request.put("roll", 1L);
        request.put("name", "Sujan");

        restTemplate.postForEntity(rootUrl.concat("/students"),new HttpEntity<>(request),Map.class);

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students/{roll}"),
                DELETE,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                roll
        );

        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteStudentWithInvalidRoll() {
        Long invalidRoll = 100L;
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                rootUrl.concat("/students/{invalidRoll}"),
                DELETE,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                invalidRoll
        );

        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }


    void deleteCreatedStudent(Long roll) {
        restTemplate.delete(rootUrl.concat("/students/{roll}"), roll);
    }

}

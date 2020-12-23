package com.github.mitrakumarsujan.springmongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceImplIntegrationTest extends StudentServiceIntegrationTest {

    @Autowired
    protected StudentServiceImplIntegrationTest(StudentServiceImpl studentService) {
        super(studentService);
    }
}

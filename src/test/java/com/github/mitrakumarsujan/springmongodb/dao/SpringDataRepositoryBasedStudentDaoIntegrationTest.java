package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataRepositoryBasedStudentDaoIntegrationTest extends StudentDaoIntegrationTest {

    @Autowired
    public SpringDataRepositoryBasedStudentDaoIntegrationTest(SpringDataRepositoryBasedStudentDao studentDao) {
        super(studentDao);
    }
}
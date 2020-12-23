package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoTemplateBasedStudentDaoIntegrationTest extends StudentDaoIntegrationTest {

    @Autowired
    public MongoTemplateBasedStudentDaoIntegrationTest(MongoTemplateBasedStudentDao studentDao) {
        super(studentDao);
    }
}
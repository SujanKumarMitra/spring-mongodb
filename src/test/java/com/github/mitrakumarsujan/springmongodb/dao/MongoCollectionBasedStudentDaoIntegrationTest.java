package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoCollectionBasedStudentDaoIntegrationTest extends StudentDaoIntegrationTest {

    @Autowired
    public MongoCollectionBasedStudentDaoIntegrationTest(MongoCollectionBasedStudentDao studentDao) {
        super(studentDao);
    }
}
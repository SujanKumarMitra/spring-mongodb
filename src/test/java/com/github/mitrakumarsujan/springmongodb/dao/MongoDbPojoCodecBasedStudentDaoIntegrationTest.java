package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoDbPojoCodecBasedStudentDaoIntegrationTest extends StudentDaoIntegrationTest {

    @Autowired
    public MongoDbPojoCodecBasedStudentDaoIntegrationTest(MongoDbPojoCodecBasedStudentDao studentDao) {
        super(studentDao);
    }
}
package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoDbPojoCodecBasedStudentDaoTest extends StudentDaoTest {

    @Autowired
    public MongoDbPojoCodecBasedStudentDaoTest(MongoDbPojoCodecBasedStudentDao studentDao) {
        super(studentDao);
    }
}
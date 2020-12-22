package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoTemplateBasedStudentDaoTest extends StudentDaoTest {

    @Autowired
    public MongoTemplateBasedStudentDaoTest(MongoTemplateBasedStudentDao studentDao) {
        super(studentDao);
    }
}
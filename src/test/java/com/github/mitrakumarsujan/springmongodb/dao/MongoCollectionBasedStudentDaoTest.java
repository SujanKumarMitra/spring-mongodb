package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoCollectionBasedStudentDaoTest extends StudentDaoTest {

    @Autowired
    public MongoCollectionBasedStudentDaoTest(MongoCollectionBasedStudentDao studentDao) {
        super(studentDao);
    }
}
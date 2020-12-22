package com.github.mitrakumarsujan.springmongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataRepositoryBasedStudentDaoTest extends StudentDaoTest {

    @Autowired
    public SpringDataRepositoryBasedStudentDaoTest(SpringDataRepositoryBasedStudentDao studentDao) {
        super(studentDao);
    }
}
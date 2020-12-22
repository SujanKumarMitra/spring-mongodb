package com.github.mitrakumarsujan.springmongodb.service;

import com.github.mitrakumarsujan.springmongodb.dao.StudentDao;
import com.github.mitrakumarsujan.springmongodb.model.StudentImpl;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceImplTest extends StudentServiceTest {

    @Autowired
    protected StudentServiceImplTest(StudentServiceImpl studentService) {
        super(studentService);
    }
}

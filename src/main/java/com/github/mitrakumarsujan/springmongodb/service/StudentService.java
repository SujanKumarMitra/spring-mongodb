package com.github.mitrakumarsujan.springmongodb.service;

import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;

public interface StudentService {

    Student createStudent(Student student) throws StudentAlreadyExistsException;

    Student getStudent(Long roll) throws StudentNotFoundException;

    Student updateStudent(Student student) throws StudentNotFoundException;

    void deleteStudent(Long roll) throws StudentNotFoundException;

}

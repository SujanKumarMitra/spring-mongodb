package com.github.mitrakumarsujan.springmongodb.service;

import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;
import com.github.mitrakumarsujan.springmongodb.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student) throws StudentAlreadyExistsException;

    default List<Student> getStudents(Integer limit) {
        return getStudents(0, limit);
    }

    List<Student> getStudents(Integer page, Integer limit);

    Student getStudent(Long roll) throws StudentNotFoundException;

    Student updateStudent(Student student) throws StudentNotFoundException;

    void deleteStudent(Long roll) throws StudentNotFoundException;

}

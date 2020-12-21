package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.Student;

public interface StudentDao {

    Boolean createStudent(Student newStudent);
    Student getStudent(Long roll);
    Boolean updateStudent(Student updatedStudent);
    Boolean deleteStudent(Long roll);
}

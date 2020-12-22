package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.Student;

import java.util.List;

public interface StudentDao {


    Boolean createStudent(Student newStudent);

    default List<Student> getStudents(Integer limit) {
        return getStudents(0, limit);
    }

    List<Student> getStudents(Integer skip, Integer limit);

    Student getStudent(Long roll);

    Boolean updateStudent(Student updatedStudent);

    Boolean deleteStudent(Long roll);
}

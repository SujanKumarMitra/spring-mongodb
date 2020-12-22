package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.SpringDataMongoDbAnnotatedStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class SpringDataRepositoryBasedStudentDao implements StudentDao {

    private StudentRepository studentRepository;

    @Autowired
    public SpringDataRepositoryBasedStudentDao(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Boolean createStudent(Student newStudent) {
        SpringDataMongoDbAnnotatedStudent student = new SpringDataMongoDbAnnotatedStudent(newStudent);
        try {
            studentRepository.insert(student);
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public List<Student> getStudents(Integer skip, Integer limit) {
        return Collections.unmodifiableList(studentRepository.findAllBySkipAndLimit(skip, limit));
    }

    @Override
    public Student getStudent(Long roll) {
        return studentRepository
                .findFirstByRoll(roll)
                .orElseGet(() -> null);
    }

    @Override
    public Boolean updateStudent(Student updatedStudent) {
        SpringDataMongoDbAnnotatedStudent student = studentRepository
                .findFirstByRoll(updatedStudent.getRoll())
                .orElseGet(() -> null);

        if (student == null)
            return false;

        student.setName(updatedStudent.getName());
        studentRepository.save(student);
        return true;
    }

    @Override
    public Boolean deleteStudent(Long roll) {

        Optional<SpringDataMongoDbAnnotatedStudent> deletedStudent = studentRepository.deleteSingleByRoll(roll);
        return deletedStudent.isPresent();
    }
}

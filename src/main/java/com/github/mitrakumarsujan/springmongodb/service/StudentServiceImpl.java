package com.github.mitrakumarsujan.springmongodb.service;

import com.github.mitrakumarsujan.springmongodb.dao.StudentDao;
import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.text.MessageFormat.format;

@Service
public class StudentServiceImpl implements StudentService {

    public static final Integer PAGE_SIZE = 10;
    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(@Qualifier("mongoTemplateBasedStudentDao") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student createStudent(Student student) throws StudentAlreadyExistsException {
        Boolean created = studentDao.createStudent(student);
        if (!created) {
            throw new StudentAlreadyExistsException(format("Student already exists with roll=[{0}]", student.getRoll()));
        }
        return student;
    }

    @Override
    public List<Student> getStudents(Integer page, Integer limit) {
        return studentDao.getStudents(PAGE_SIZE * page, limit);
    }

    @Override
    public Student getStudent(Long roll) throws StudentNotFoundException {
        Student student = studentDao.getStudent(roll);
        if (student == null)
            throw new StudentNotFoundException(format("No student found with roll=[{0}]", roll));
        return student;
    }

    @Override
    public Student updateStudent(Student student) throws StudentNotFoundException {
        Boolean updated = studentDao.updateStudent(student);
        if (!updated) {
            throw new StudentNotFoundException(format("No student found with roll =[{0}]", student.getRoll()));
        }
        return student;

    }

    @Override
    public void deleteStudent(Long roll) throws StudentNotFoundException {
        Boolean deleted = studentDao.deleteStudent(roll);
        if (!deleted) {
            throw new StudentNotFoundException(format("No student found with roll=[{0}]", roll));
        }
    }
}

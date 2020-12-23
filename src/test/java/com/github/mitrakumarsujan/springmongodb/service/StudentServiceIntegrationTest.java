package com.github.mitrakumarsujan.springmongodb.service;

import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.github.mitrakumarsujan.springmongodb.model.StudentImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

abstract class StudentServiceIntegrationTest {

    public static final long ROLL_NOT_PRESENT_IN_DB = 10L;
    protected StudentService studentService;
    protected List<Student> insertedStudents;

    protected StudentServiceIntegrationTest(StudentService studentService) {
        this.studentService = studentService;
    }

    @BeforeEach
    void setUp() {
        this.insertedStudents = new LinkedList<>();
        for (Student student : getDummyStudents()) {
            try {
                Student insertedStudent = studentService.createStudent(student);
                insertedStudents.add(insertedStudent);
            } catch (StudentAlreadyExistsException e) {
                fail("student already exists in db.");
            }
        }
    }

    @AfterEach
    void tearDown() {
        for (Student student : insertedStudents) {
            try {
                studentService.deleteStudent(student.getRoll());
            } catch (StudentNotFoundException e) {
                fail("student already exists in db");
            }
        }
        insertedStudents = null;
    }

    private List<Student> getDummyStudents() {
        return asList(
                new StudentImpl(1L, "Tom"),
                new StudentImpl(2l, "Jerry"),
                new StudentImpl(3L, "Spike")
        );
    }

    @Test
    void testCreateValidStudent() {
        Student student = new StudentImpl(ROLL_NOT_PRESENT_IN_DB, "Test Name");
        Student createdStudent = studentService.createStudent(student);

        assertThat(createdStudent).isEqualTo(student);

        insertedStudents.add(createdStudent); // deletes the student on cleanup
    }

    @Test
    void testShouldThrowExceptionOnCreateExistingStudent() {
        Student student = new StudentImpl(ROLL_NOT_PRESENT_IN_DB, "Test Name");
        Student createdStudent = studentService.createStudent(student);

        insertedStudents.add(createdStudent);
        assertThrows(StudentAlreadyExistsException.class, () -> studentService.createStudent(student));
    }

    @Test
    void testGetStudents() {
        List<Student> shouldHaveAllStudents = studentService.getStudents(0, insertedStudents.size());
        assertTrue(shouldHaveAllStudents.containsAll(insertedStudents));

    }

    @Test
    void testGetStudentsWithSkip() {
        List<Student> shouldBeEmptyList = studentService.getStudents(insertedStudents.size(), 1);
        assertEquals(0, shouldBeEmptyList.size());
    }

    @Test
    void testGetStudentsWithLimit() {
        int limitCount = insertedStudents.size() - 1;
        List<Student> shouldBeEmptyList = studentService.getStudents(limitCount);
        assertEquals(limitCount, shouldBeEmptyList.size());
    }

    @Test
    void testUpdateValidStudent() {
        Student student = this.insertedStudents.get(0);
        String updatedName = "Changed Name";

        student.setName(updatedName);
        assertDoesNotThrow(() -> studentService.updateStudent(student));

        Student updatedStudent = studentService.getStudent(student.getRoll());

        assertThat(updatedStudent.getName()).isEqualTo(updatedName);
    }

    @Test
    void testUpdateInvalidStudent() {
        Student student = new StudentImpl(ROLL_NOT_PRESENT_IN_DB, "Name");
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(student));
    }


    @Test
    void testDeleteValidStudent() {
        Student student = insertedStudents.get(0);
        Long roll = student.getRoll();
        Executable executable = () -> studentService.deleteStudent(roll);

        assertDoesNotThrow(executable);
        assertThrows(StudentNotFoundException.class, executable);

        insertedStudents.remove(0);
    }

    @Test
    void testShouldThrowExceptionOnDeleteInvalidStudent() {
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(ROLL_NOT_PRESENT_IN_DB));
    }

}
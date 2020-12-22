package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.SimpleStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.github.mitrakumarsujan.springmongodb.model.StudentImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;
import static org.junit.jupiter.api.Assertions.*;

abstract class StudentDaoTest {

    protected static final long ROLL_NOT_PRESENT_IN_DB = 999L;
    protected StudentDao dao;
    protected List<Student> insertedStudents;

    protected StudentDaoTest(StudentDao dao) {
        Objects.requireNonNull(dao);
        this.dao = dao;
    }

    protected List<Student> getStudentsToInsert() {
        return Arrays.asList(
                new StudentImpl(1L, "Tom"),
                new StudentImpl(2L, "Jerry"),
                new StudentImpl(3L, "Spike"),
                new StudentImpl(4L, "Nibbles"),
                new StudentImpl(5L, "Tyke")
        );
    }

    @BeforeEach
    void setUp() {
        this.insertedStudents = new LinkedList<>(getStudentsToInsert());
        boolean allInserted = this.insertedStudents
                .stream()
                .sequential() // maintain order
                .map(dao::createStudent)
                .allMatch(Boolean::booleanValue); //all students should get inserted

        if (!allInserted) {
            fail("Dummy Student Data not inserted.. check createStudent()");
        }

    }

    @AfterEach
    void tearDown() {
        boolean allDeleted = this.insertedStudents
                .stream()
                .parallel() // concurrent deletion is ok
                .map(Student::getRoll)
                .map(dao::deleteStudent)
                .allMatch(Boolean::booleanValue);

        if (!allDeleted) {
            fail("all students are not deleted. check deleteStudent()");
        }
        this.insertedStudents = null;
    }

    @Test
    void testCreateValidStudent() {
        Long roll = ROLL_NOT_PRESENT_IN_DB;
        Student shouldGetInsertedStudent = new StudentImpl(roll, "Test Name");
        Boolean created = dao.createStudent(shouldGetInsertedStudent);
        assertTrue(created, () -> "student not created. Check createStudent() method");

        insertedStudents.add(shouldGetInsertedStudent); //gets deleted on cleanUp
        Student fetchedStudent = dao.getStudent(roll);

        assertEquals(roll, fetchedStudent.getRoll(), () -> "Roll not matching");
        assertEquals(shouldGetInsertedStudent.getName(), fetchedStudent.getName()
                , () -> "Name not matching");

    }

    @Test
    void testCreateInvalidStudent() {
        Student insertedStudent = insertedStudents.get(0);
        Boolean created = dao.createStudent(insertedStudent);
        assertFalse(created, () -> "Student already exists and shouldn't be created again." +
                " Check createStudent() method");
    }

    @Test
    void testGetStudents() {
        int insertedStudentsCount = insertedStudents.size();
        List<Student> fetchedStudents = dao.getStudents(insertedStudentsCount);
        assertEquals(fetchedStudents.size(), insertedStudentsCount,
                () -> "Count of fetched students not matching. Check getStudents()");
        assertTrue(fetchedStudents.containsAll(insertedStudents),
                () -> "fetched students not matching with inserted students");
    }

    @Test
    void testGetStudentsWithSkip() {
        int insertedStudentsCount = insertedStudents.size();
        List<Student> shouldBeEmptyList = dao.getStudents(
                insertedStudentsCount,
                1); //  limit shouldn't matter; should fetch empty list
        assertTrue(shouldBeEmptyList.isEmpty(), () -> "should fetch empty list. check getStudents()");
    }

    @Test
    void testGetStudentsWithLimit() {
        int insertedStudentsCount = insertedStudents.size();
        int limitValue = insertedStudentsCount - 2;
        List<Student> shouldHave_limitValue_students = dao.getStudents(limitValue);
        assertEquals(shouldHave_limitValue_students.size(),
                limitValue,
                () -> format("should fetch {0} students", limitValue));
    }

    @Test
    void testGetStudent() {
        Student insertedStudent = insertedStudents.get(0);
        Student fetchedStudent = dao.getStudent(insertedStudent.getRoll());

        assertNotNull(fetchedStudent, () -> "Should fetch inserted student");

        assertEquals(insertedStudent.getRoll(), fetchedStudent.getRoll(), () -> "Roll not matching");
        assertEquals(insertedStudent.getName(), fetchedStudent.getName(), () -> "Name not matching");
    }

    @Test
    void testShouldNotGetStudent() {
        Student fetchedStudent = dao.getStudent(ROLL_NOT_PRESENT_IN_DB);
        assertNull(fetchedStudent, () -> "Should not fetch student");
    }

    @Test
    void testUpdateValidStudent() {
        Student shouldBeUpdatedStudent = this.insertedStudents.get(0);
        shouldBeUpdatedStudent.setName("Updated Name");

        Boolean result = dao.updateStudent(shouldBeUpdatedStudent);
        assertTrue(result, () -> "Should be updated");

        Student updatedStudent = dao.getStudent(shouldBeUpdatedStudent.getRoll());

        assertEquals(updatedStudent.getName(), shouldBeUpdatedStudent.getName(), () -> "Name not matching");
        assertEquals(updatedStudent.getRoll(), shouldBeUpdatedStudent.getRoll(), () -> "Roll not matching");
    }

    @Test
    void testUpdateInvalidStudent() {
        Student invalidStudent = new StudentImpl(ROLL_NOT_PRESENT_IN_DB, "Invalid Student Name");
        Boolean updated = dao.updateStudent(invalidStudent);

        assertFalse(updated, () -> "Should not be updated");
    }

    @Test
    void testDeleteValidStudent() {
        int index = 0;
        Student validStudent = insertedStudents.get(index);
        Boolean deleted = dao.deleteStudent(validStudent.getRoll());
        assertTrue(deleted, () -> "Should get deleted");

        Student student = dao.getStudent(validStudent.getRoll());
        assertNull(student, () -> "Should be null");

        insertedStudents.remove(index);
    }

    @Test
    void testDeleteInvalidStudent() {
        Boolean deleted = dao.deleteStudent(ROLL_NOT_PRESENT_IN_DB);
        assertFalse(deleted, () -> "Should not get deleted");
    }

}

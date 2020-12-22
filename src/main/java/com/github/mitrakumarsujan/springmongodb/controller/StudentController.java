package com.github.mitrakumarsujan.springmongodb.controller;

import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;
import com.github.mitrakumarsujan.springmongodb.model.CreateStudentRequest;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.github.mitrakumarsujan.springmongodb.model.UpdateStudentRequest;
import com.github.mitrakumarsujan.springmongodb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStudents(
            @RequestParam(name = "skip", defaultValue = "0") Integer skip,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit) {

        List<Student> students = studentService.getStudents(skip, limit);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("skipped", skip);
        response.put("limit", students.size());
        response.put("students", students);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Map<String, Object>> getStudent(@PathVariable("roll") Long roll) {
        Student student = studentService.getStudent(roll);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("student", student);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createStudent(
            @RequestBody @Valid CreateStudentRequest student) {
        Student createdStudent = studentService.createStudent(student);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", "Student created successfully");
        response.put("student", createdStudent);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @PutMapping("/{roll}")
    public ResponseEntity<Map<String, Object>> updateStudent(
            @PathVariable("roll") Long roll,
            @RequestBody @Valid UpdateStudentRequest student) {
        student.setRoll(roll);
        Student updatedStudent = studentService.updateStudent(student);

        Map<String, Object> response = buildResponse(OK,
                format("student with roll[{0}] updated", roll));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roll}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long roll) {
        studentService.deleteStudent(roll);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", format("Student with roll=[{0}] is deleted", roll));

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentNotFoundException exception) {
        Map<String, Object> response = buildResponse(NOT_FOUND, exception.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleStudentAlreadyExists(StudentAlreadyExistsException exception) {
        Map<String, Object> response = buildResponse(CONFLICT, exception.getMessage());
        return ResponseEntity
                .status(CONFLICT)
                .body(response);
    }

    private Map<String, Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("error", status);
        response.put("message", message);

        return response;
    }

}

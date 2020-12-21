package com.github.mitrakumarsujan.springmongodb;

import com.github.mitrakumarsujan.springmongodb.exception.StudentAlreadyExistsException;
import com.github.mitrakumarsujan.springmongodb.exception.StudentNotFoundException;
import com.github.mitrakumarsujan.springmongodb.model.SimpleStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.github.mitrakumarsujan.springmongodb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Map<String, Object>> getStudent(@PathVariable("roll") Long roll) {
        Student student = studentService.getStudent(roll);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("student", student);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createStudent(@RequestBody SimpleStudent student) {
        Student createdStudent = studentService.createStudent(student);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Student created successfully");
        response.put("student", createdStudent);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @DeleteMapping("/{roll}")
    public ResponseEntity<Map<String,Object>> deleteStudent(@PathVariable Long roll) {
        studentService.deleteStudent(roll);

        Map<String, Object> response = new HashMap<>();
        response.put("success",true);
        response.put("message", format("Student with roll=[{0}] is deleted",roll));

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
        Map<String, Object> response = new HashMap<>();

        response.put("error", status);
        response.put("message", message);

        return response;
    }

}

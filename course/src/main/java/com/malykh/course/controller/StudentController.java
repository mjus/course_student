package com.malykh.course.controller;

import com.malykh.course.exeption.ResourseNotFoundExeption;
import com.malykh.course.model.Student;
import com.malykh.course.repository.StudentRepository;
import com.malykh.course.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Get all students", description = "Get list of all students")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Operation(summary = "Create student", description = "Create student")
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @Operation(summary = "Get student by Id", description = "Get all information about the student and what courses they are enrolled in")
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourseNotFoundExeption("Student not exist with id:" + id));
        return ResponseEntity.ok(student);
    }

    @Operation(summary = "Update student", description = "Update student")
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student studentDetails) {
        Student updateStudent = studentRepository.findById(id).orElseThrow(() -> new ResourseNotFoundExeption("Student not exist with id:" + id));
        updateStudent.setName(studentDetails.getName());
        updateStudent.setLastName(studentDetails.getLastName());
        updateStudent.setBirthdate(studentDetails.getBirthdate());
        studentRepository.save(updateStudent);
        return ResponseEntity.ok(updateStudent);
    }

    @Operation(summary = "Delete student", description = "Delete student")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundExeption("Student not exist with id:" + id));
        studentRepository.delete(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enroll student to course", description = "Enroll student to course")
    @PutMapping("{student_id}/enroll/{course_id}")
    public Student enrollStudentToCourse(@PathVariable long student_id, @PathVariable long course_id) {
        return studentService.enrollStudentToCourse(student_id, course_id);
    }

    @Operation(summary = "Delete student from course", description = "Delete student from course")
    @DeleteMapping("{student_id}/remove/{course_id}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable long student_id, @PathVariable long course_id) {
        studentService.removeStudentFromCourse(student_id, course_id);
        return ResponseEntity.ok().build();
    }
}

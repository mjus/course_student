package com.malykh.student.controller;

import com.malykh.student.StudentApplication;
import com.malykh.student.exeption.ResourseNotFoundExeption;
import com.malykh.student.model.Student;
import com.malykh.student.repository.StudentRepository;
import com.malykh.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    public static final String exchangeName = "testExchange";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String courseMicroserviceUrl = "http://course:8080/api/v1";

    @Operation(summary = "Get all students", description = "Get list of all students")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Operation(summary = "Create student", description = "Create student in two databases (courses, student)")
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        restTemplate.postForEntity(courseMicroserviceUrl + "/students", request, Student.class);
        return ResponseEntity.ok(studentRepository.save(student));
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

    @Operation(summary = "Enroll student to course", description = "Enroll student to course in both databases(courses, students)")
    @PutMapping("{student_id}/enroll/{course_id}")
    public Student enrollStudentToCourse(@PathVariable long student_id, @PathVariable long course_id) {
        String url = courseMicroserviceUrl + "/students/" + student_id + "/enroll/" + course_id;
        restTemplate.put(url, null);
        return studentService.enrollStudentToCourse(student_id, course_id);
    }

    @Operation(summary = "Delete student from course", description = "Delete student from course in both databases(courses, students)")
    @DeleteMapping("{student_id}/remove/{course_id}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable long student_id, @PathVariable long course_id) {
//        String message = student_id + "," + course_id;
//        rabbitTemplate.convertAndSend(exchangeName, "first.key", message);
        String url = courseMicroserviceUrl + "/students/" + student_id + "/remove/" + course_id;
        webClientBuilder.build()
                .delete()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .subscribe(); //

        studentService.removeStudentFromCourse(student_id, course_id);
        return ResponseEntity.ok().build();
    }
}

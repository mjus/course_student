package com.malykh.student.controller;

import com.malykh.student.exeption.ResourseNotFoundExeption;
import com.malykh.student.model.Course;
import com.malykh.student.model.Student;
import com.malykh.student.repository.CourseRepository;
import com.malykh.student.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Get all courses", description = "Get a list of all courses")
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Operation(summary = "Get all students of course", description = "Get a list of all students of course")
    @GetMapping("/{courseId}/students")
    public ResponseEntity<Set<Student>> getAllStudentsOfCourse(@PathVariable long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourseNotFoundExeption("Course not exist with id:" + courseId));
        return ResponseEntity.ok(course.getStudents());
    }

    @Operation(summary = "Create course", description = "Create course")
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @Operation(summary = "Get course by Id", description = "Get course by Id")
    @GetMapping("{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourseNotFoundExeption("Course not exist with id:" + id));
        return ResponseEntity.ok(course);
    }

    @Operation(summary = "Update course", description = "Update course")
    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable long id, @RequestBody Course courseDetails) {
        Course updateCourse = courseRepository.findById(id).orElseThrow(() -> new ResourseNotFoundExeption("Course not exist with id:" + id));
        updateCourse.setName(courseDetails.getName());
        updateCourse.setDescription(courseDetails.getDescription());
        updateCourse.setStartDate(courseDetails.getStartDate());
        updateCourse.setFinalDate(courseDetails.getFinalDate());
        updateCourse.setStudents(courseDetails.getStudents());
        courseRepository.save(updateCourse);
        return ResponseEntity.ok(updateCourse);
    }

    @Operation(summary = "Delete course", description = "Delete course by Id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundExeption("Course not exist with id:" + id));
        courseService.deleteCourse(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

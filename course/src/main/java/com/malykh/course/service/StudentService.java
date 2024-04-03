package com.malykh.course.service;

import com.malykh.course.model.Course;
import com.malykh.course.model.Student;
import com.malykh.course.repository.CourseRepository;
import com.malykh.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Student enrollStudentToCourse(long studentId, long courseId) {
        Set<Course> courses;
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();
        courses = student.getCourses();
        courses.add(course);
        student.setCourses(courses);
        return studentRepository.save(student);
    }

    public void removeStudentFromCourse(long studentId, long courseId) {
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();
        student.getCourses().remove(course);
        studentRepository.save(student);
    }
}

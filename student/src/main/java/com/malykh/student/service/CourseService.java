package com.malykh.student.service;

import com.malykh.student.model.Course;
import com.malykh.student.model.Student;
import com.malykh.student.repository.CourseRepository;
import com.malykh.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void deleteCourse(Course course) {
        Set<Student> students = course.getStudents();

        for (Student student : students) {
            student.getCourses().remove(course);
            studentRepository.save(student);
        }
        courseRepository.delete(course);
    }
}
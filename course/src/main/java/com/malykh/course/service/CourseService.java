package com.malykh.course.service;

import com.malykh.course.model.Student;
import com.malykh.course.model.Course;
import com.malykh.course.repository.CourseRepository;
import com.malykh.course.repository.StudentRepository;
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
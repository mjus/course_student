package com.malykh.course;

import com.malykh.course.model.Course;
import com.malykh.course.model.Student;
import com.malykh.course.repository.CourseRepository;
import com.malykh.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class CourseApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        Course course1 = new Course();
        course1.setName("firstCourse");
        course1.setDescription("firstCourse");
        course1.setStartDate(new Date(2024, 04, 1));
        course1.setFinalDate(new Date(2024, 05, 1));

        Course course2 = new Course();
        course2.setName("secondCourse");
        course2.setDescription("secondCourse");
        course2.setStartDate(new Date(2024, 04, 1));
        course2.setFinalDate(new Date(2024, 05, 1));
        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);
        Student student1 = new Student();
        student1.setName("Tom");
        student1.setLastName("Black");
        student1.setBirthdate(new Date(1985, 3, 3));
        student1.setCourses(courses);


        studentRepository.save(student1);
    }
}

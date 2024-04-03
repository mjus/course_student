package com.malykh.student.repository;

import com.malykh.student.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course, Long> {
}

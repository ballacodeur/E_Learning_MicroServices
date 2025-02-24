package com.examn_elearning.courses_service.repository;

import com.examn_elearning.courses_service.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course ,String> {

}

package com.examn_elearning.courses_service.repository;

import com.examn_elearning.courses_service.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
}

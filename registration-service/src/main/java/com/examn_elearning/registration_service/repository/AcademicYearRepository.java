package com.examn_elearning.registration_service.repository;

import com.examn_elearning.registration_service.entites.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicYearRepository extends JpaRepository<AcademicYear,String> {
}

package com.examn_elearning.registration_service.repository;

import com.examn_elearning.registration_service.entites.Registrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registrations,String> {
}

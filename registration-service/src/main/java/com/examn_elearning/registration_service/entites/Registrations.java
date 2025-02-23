package com.examn_elearning.registration_service.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class Registrations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID registrationId;
    private Date date;
    private String description;
    private boolean archive;

    private Long studentId;
    private Long academicYearId;
    private Long classId;
}


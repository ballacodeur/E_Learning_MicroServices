package com.examn_elearning.registration_service.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID academicYearID;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private boolean archive;
}


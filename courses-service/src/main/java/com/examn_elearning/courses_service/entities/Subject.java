package com.examn_elearning.courses_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID subjectID;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private boolean archive;
}


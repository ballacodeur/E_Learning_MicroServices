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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseID;

    @Column(nullable = false)
    private String name;

    private String description;
    private boolean archive;

    private UUID subjectID;  // Matière associée
    private UUID classeID;   // Classe associée
    private UUID teacherID;  // Professeur qui donne le cours
}

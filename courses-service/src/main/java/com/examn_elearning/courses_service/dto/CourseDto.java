package com.examn_elearning.courses_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDto {

    private UUID courseID;

    @NotBlank(message = "Le nom du cours est obligatoire")
    private String name;

    private String description;
    private boolean archive;

    @NotNull(message = "La matière est obligatoire")
    private UUID subjectID;

    @NotNull(message = "La classe associée est obligatoire")
    private UUID classeID;

    @NotNull(message = "Le professeur associé est obligatoire")
    private UUID teacherID;
}


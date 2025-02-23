package com.examn_elearning.registration_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcademicYearDto {

    private UUID academicYearID;

    @NotBlank(message = "Le nom de l'année académique est obligatoire")
    private String name;

    private String description;

    private boolean archive;
}


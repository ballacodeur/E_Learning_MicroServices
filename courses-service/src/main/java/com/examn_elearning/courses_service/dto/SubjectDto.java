package com.examn_elearning.courses_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectDto {

    private UUID subjectID;

    @NotBlank(message = "Le nom de la matière est obligatoire")
    private String name;

    private String description;
    private boolean archive;
}

package com.examn_elearning.registration_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationDto {

    private UUID registrationID;

    @NotNull(message = "La date de l'inscription est obligatoire")
    private String date; // ou LocalDate/LocalDateTime selon ton choix

    private String description;

    private boolean archive;

    // Références par ID pour respecter l'architecture microservices
    @NotBlank(message = "L'identifiant de l'étudiant est obligatoire")
    private String studentID;

    @NotBlank(message = "L'identifiant de la classe est obligatoire")
    private String classeID;

    @NotBlank(message = "L'identifiant de l'année académique est obligatoire")
    private String academicYearID;
}


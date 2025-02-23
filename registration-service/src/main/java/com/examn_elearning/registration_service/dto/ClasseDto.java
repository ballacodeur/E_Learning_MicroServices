package com.examn_elearning.registration_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClasseDto {

    private UUID classeID;

    @NotBlank(message = "Le nom de la classe est obligatoire")
    private String name;

    private String description;

    // Exemple : pour savoir si la classe est archivée ou non
    private boolean archive;
}

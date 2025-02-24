package com.examen.e_learning.User_Service.users.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto {

    @NotBlank(message = "Le numéro d'inscription est requis")
    private String registrationNu;

    private Long registrationId;
    private Long classId;
}


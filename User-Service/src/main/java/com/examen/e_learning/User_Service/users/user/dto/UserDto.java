package com.examen.e_learning.User_Service.users.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Le prénom est requis")
    private String firstName;

    @NotBlank(message = "Le nom est requis")
    private String lastName;

    @NotBlank(message = "Le profil est requis")
    private String profileType; // "STUDENT", "TEACHER", "ADMIN"

    @Email
    @NotBlank(message = "L'email est requis")
    private String emailPro;

    private String phoneNumber;
    private String address;

    private String registrationNu; // Seulement pour Student
    private Long classId;
}

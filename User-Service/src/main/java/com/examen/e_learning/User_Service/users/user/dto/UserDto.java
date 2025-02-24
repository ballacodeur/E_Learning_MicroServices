package com.examen.e_learning.User_Service.users.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    protected Long id;

    @NotBlank(message = "Le prénom est requis")
    protected String firstName;

    @NotBlank(message = "Le nom est requis")
    protected String lastName;

    @NotBlank(message = "Le profil est requis")
    protected String profileType; // "STUDENT", "TEACHER", "ADMIN"

    @Email
    @NotBlank(message = "L'email est requis")
    protected String emailPro;

    protected String phoneNumber;
    protected String address;

    protected String registrationNu; // Seulement pour Student

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;
    protected Long classId;
}

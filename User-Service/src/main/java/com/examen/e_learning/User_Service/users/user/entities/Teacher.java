package com.examen.e_learning.User_Service.users.user.entities;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("TEACHER") // La valeur qui sera stockée dans la colonne "profil"
public class Teacher extends User {
    private Long courseId;
}

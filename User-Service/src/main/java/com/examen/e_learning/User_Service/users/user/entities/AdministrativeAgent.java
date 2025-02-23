package com.examen.e_learning.User_Service.users.user.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@DiscriminatorValue("ADMIN") // La valeur qui sera stockée dans la colonne "profil"
public class AdministrativeAgent extends User {
}

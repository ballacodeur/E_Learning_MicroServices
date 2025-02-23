package com.examen.e_learning.User_Service.users.user.entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Column(nullable = false, unique = true)
    private String registrationNu;
    private Long registrationId;
    private Long classId;
}

package com.examen.e_learning.User_Service.users.user.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Une seule table pour tous les utilisateurs
@DiscriminatorColumn(name = "profil", discriminatorType = DiscriminatorType.STRING)
public  class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailPro;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String emailPerso;
    private String phoneNumber;
    private String address;
    private boolean archive;
}


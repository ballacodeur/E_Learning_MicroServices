package com.examen.e_learning.User_Service.users.user.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String emailPro;

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    protected String emailPerso;
    protected String phoneNumber;
    protected String address;
    protected String profileType;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;
    protected boolean archive;
}


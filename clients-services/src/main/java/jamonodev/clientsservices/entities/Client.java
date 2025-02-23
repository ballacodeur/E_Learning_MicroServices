package jamonodev.clientsservices.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter  @Getter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID clientID;
    private String nom;
    private String adresse;
    @Column(nullable = false)
    private String siteID;
}

package jamonodev.produits_services.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter  @Getter
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID produitID;
    private String nom;
    private String description;
    private double quantite;
    private double seuil;
    @Column(nullable = false)
    private String siteID;
}

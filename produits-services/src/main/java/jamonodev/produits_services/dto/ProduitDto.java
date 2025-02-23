package jamonodev.produits_services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProduitDto {
    private UUID produitID;

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String nom;

    @NotBlank(message = "La description du produit est obligatoire")
    private String description;

    @NotNull(message = "La quantite du produit est obligatoire")
    private double quantite;

    @NotNull(message = "Le quantite seuil du produit est obligatoire")
    private double seuil;

    @NotBlank(message = "Le client doit etre associe a un site")
    private String siteID;
}

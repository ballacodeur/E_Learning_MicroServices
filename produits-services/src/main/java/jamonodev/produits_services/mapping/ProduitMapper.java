package jamonodev.produits_services.mapping;


import jamonodev.produits_services.dto.ProduitDto;
import jamonodev.produits_services.entities.Produit;
import org.mapstruct.Mapper;

@Mapper
public interface ProduitMapper {
    ProduitDto toDto(Produit produit);
    Produit toEntity(ProduitDto produitDto);
}

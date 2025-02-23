package jamonodev.produits_services.services;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jamonodev.produits_services.dao.ProduitRepository;
import jamonodev.produits_services.dto.ProduitDto;
import jamonodev.produits_services.exception.EntityNotFoundException;
import jamonodev.produits_services.mapping.ProduitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public List<ProduitDto> getAllProduitsBySites( String siteID) {
        try {
            return getAllProduits().stream()
                    .filter(c -> c.getSiteID().equalsIgnoreCase(siteID)).toList();
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération des produits", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des produits");
        }
    }
    public List<ProduitDto> getAllProduits() {
        try {
            return produitRepository.findAll().stream()
                    .map(produitMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération des produits", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des produits");
        }
    }

    public ProduitDto getProduitById(UUID produitID) {
        try {
            return produitMapper.toDto(produitRepository.findById(produitID)
                    .orElseThrow(() -> new EntityNotFoundException("Le produit est introuvable")));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Produit Introuvable");
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération du produit avec l'ID {}", produitID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération du produit");
        }
    }

    public ProduitDto saveProduit(ProduitDto produitDto) {
        try {
            return produitMapper.toDto(produitRepository.save(produitMapper.toEntity(produitDto)));
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la sauvegarde du produit", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la sauvegarde du produit");
        }
    }

    public String updateProduit(String produitID, ProduitDto updateProduit) {
        try {
            ProduitDto existingProduit = getProduitById(UUID.fromString(produitID));
            updateProduit.setProduitID(existingProduit.getProduitID());
            produitRepository.save(produitMapper.toEntity(updateProduit));
            return "Modifications effectuées avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la mise à jour du produit avec l'ID {}", produitID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour du produit");
        }
    }

    public String deleteProduit(String produitId) {
        try {
            ProduitDto existingProduit = getProduitById(UUID.fromString(produitId));
            produitRepository.delete(produitMapper.toEntity(existingProduit));
            return "Le produit a été bien supprimé";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la suppression du produit avec l'ID {}", produitId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression du produit");
        }
    }
}

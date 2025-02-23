package jamonodev.produits_services.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jamonodev.produits_services.dto.ProduitDto;
import jamonodev.produits_services.exception.EntityNotFoundException;
import jamonodev.produits_services.services.ProduitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/produits")
@AllArgsConstructor
public class ProduitController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";
    private ProduitService produitService;


    @GetMapping("/site/{siteID}/produits")
    public ResponseEntity<?> getAllProduitsBySites(@PathVariable String siteID) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, produitService.getAllProduitsBySites(siteID));
            output.put(MESSAGE, "Liste des produits du site récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @GetMapping("/{produitID}")
    public ResponseEntity<?> getProduitById(@PathVariable("produitID") String produitID){
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, produitService.getProduitById(UUID.fromString(produitID)));
            output.put(MESSAGE, "Produit Recherche trouve avec succes");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        }catch (Exception e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProduits() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, produitService.getAllProduits());
            output.put(MESSAGE, "Liste des Produits du site avec succes");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        }catch (Exception e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> saveProduit(@Valid @RequestBody ProduitDto produitDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, produitService.saveProduit(produitDto));
            output.put(MESSAGE, " Produits ajoute avec succes");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);

        }catch (Exception e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @PutMapping("/{produitId}")
    public ResponseEntity<?> updateProduit(@PathVariable("produitId") String produitId, @RequestBody ProduitDto updateProduitDto){
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, produitService.updateProduit(produitId,updateProduitDto));
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);

        } catch (EntityNotFoundException e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @DeleteMapping("/{produitID}")
    public ResponseEntity<?> deleteProduit(@PathVariable("produitID") String produitId){
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, produitService.deleteProduit(produitId));
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);

        } catch (NotFoundException e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e){
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}

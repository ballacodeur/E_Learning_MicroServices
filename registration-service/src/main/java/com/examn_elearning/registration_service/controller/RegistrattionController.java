package com.examn_elearning.registration_service.controller;



import com.examn_elearning.registration_service.dto.RegistrationDto;
import com.examn_elearning.registration_service.services.RegistrationService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/registrations")
@AllArgsConstructor
public class RegistrattionController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final RegistrationService registrationService;

    /**
     * 🔹 Récupérer une inscription par son ID
     */
    @GetMapping("/{registrationId}")
    public ResponseEntity<?> getRegistrationById(@PathVariable("registrationId") UUID registrationId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, registrationService.getRegistrationById(registrationId));
            output.put(MESSAGE, "Inscription trouvée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, "Inscription introuvable");
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    /**
     * 🔹 Récupérer toutes les inscriptions
     */
    @GetMapping
    public ResponseEntity<?> getAllRegistrations() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, registrationService.getAllRegistrations());
            output.put(MESSAGE, "Liste des inscriptions récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de la récupération des inscriptions");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Créer une nouvelle inscription
     */
    @PostMapping
    public ResponseEntity<?> saveRegistration(@Valid @RequestBody RegistrationDto registrationDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, registrationService.saveRegistration(registrationDto));
            output.put(MESSAGE, "Inscription ajoutée avec succès");
            output.put(STATUS, 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de l'ajout de l'inscription");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}

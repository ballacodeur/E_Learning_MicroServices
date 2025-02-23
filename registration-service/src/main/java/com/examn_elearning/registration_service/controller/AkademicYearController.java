package com.examn_elearning.registration_service.controller;

import com.examn_elearning.registration_service.dto.AcademicYearDto;
import com.examn_elearning.registration_service.services.AcademicYearService;
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
@RequestMapping("/api/academic-years")
@AllArgsConstructor
public class AkademicYearController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";
    private final AcademicYearService academicYearService;

    /**
     * 🔹 Récupérer une année académique par son ID
     */
    @GetMapping("/{academicYearId}")
    public ResponseEntity<?> getAcademicYearById(@PathVariable("academicYearId") UUID academicYearId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, academicYearService.getAcademicYearById(academicYearId));
            output.put(MESSAGE, "Année académique trouvée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, "Année académique introuvable");
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    /**
     * 🔹 Récupérer toutes les années académiques
     */
    @GetMapping
    public ResponseEntity<?> getAllAcademicYears() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, academicYearService.getAllAcademicYears());
            output.put(MESSAGE, "Liste des années académiques récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de la récupération des années académiques");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Ajouter une nouvelle année académique
     */
    @PostMapping
    public ResponseEntity<?> saveAcademicYear(@Valid @RequestBody AcademicYearDto academicYearDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, academicYearService.saveAcademicYear(academicYearDto));
            output.put(MESSAGE, "Année académique ajoutée avec succès");
            output.put(STATUS, 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de l'ajout de l'année académique");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}


package com.examn_elearning.registration_service.controller;


import com.examn_elearning.registration_service.dto.ClasseDto;
import com.examn_elearning.registration_service.services.ClasseService;
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
@RequestMapping("/api/classes")
@AllArgsConstructor
public class ClasseController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final ClasseService classeService;

    /**
     * 🔹 Récupérer une classe par son ID
     */
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") UUID classId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, classeService.getClassById(classId));
            output.put(MESSAGE, "Classe trouvée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, "Classe introuvable");
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    /**
     * 🔹 Récupérer toutes les classes
     */
    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, classeService.getAllClasses());
            output.put(MESSAGE, "Liste des classes récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de la récupération des classes");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Ajouter une nouvelle classe
     */
    @PostMapping
    public ResponseEntity<?> saveClass(@Valid @RequestBody ClasseDto classeDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, classeService.saveClass(classeDto));
            output.put(MESSAGE, "Classe ajoutée avec succès");
            output.put(STATUS, 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, "Erreur lors de l'ajout de la classe");
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}


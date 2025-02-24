package com.examn_elearning.courses_service.controller;


import com.examn_elearning.courses_service.dto.SubjectDto;
import com.examn_elearning.courses_service.service.SubjectService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        Map<String, Object> output = new HashMap<>();
        output.put("data", subjectService.getAllSubjects());
        output.put("message", "Liste des matières récupérée avec succès");
        output.put("status", 200);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{subjectID}")
    public ResponseEntity<?> getSubjectById(@PathVariable UUID subjectID) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put("data", subjectService.getSubjectById(subjectID));
            output.put("message", "Matière trouvée avec succès");
            output.put("status", 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put("message", "Matière introuvable");
            output.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectDto subjectDto) {
        Map<String, Object> output = new HashMap<>();
        output.put("data", subjectService.saveSubject(subjectDto));
        output.put("message", "Matière ajoutée avec succès");
        output.put("status", 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @DeleteMapping("/{subjectID}")
    public ResponseEntity<?> deleteSubject(@PathVariable UUID subjectID) {
        Map<String, Object> output = new HashMap<>();
        output.put("message", subjectService.deleteSubject(subjectID));
        output.put("status", 200);
        return ResponseEntity.ok(output);
    }
}


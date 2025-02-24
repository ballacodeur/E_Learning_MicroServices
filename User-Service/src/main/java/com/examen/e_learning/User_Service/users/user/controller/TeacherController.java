package com.examen.e_learning.User_Service.users.user.controller;

import com.examen.e_learning.User_Service.users.user.dto.TeacherDto;
import com.examen.e_learning.User_Service.users.user.service.TeacherService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
@Slf4j
public class TeacherController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final TeacherService teacherService;

    /**
     * 🔹 Récupérer un enseignant par ID
     */
    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable("teacherId") Long teacherId) {
        Map<String, Object> output = new HashMap<>();
        try {
            Optional<TeacherDto> teacher = teacherService.getTeacher(teacherId);
            if (teacher.isPresent()) {
                output.put(RESULT, teacher.get());
                output.put(MESSAGE, "Enseignant trouvé avec succès");
                output.put(STATUS, 200);
                return ResponseEntity.ok(output);
            } else {
                throw new NotFoundException("Enseignant introuvable");
            }
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    /**
     * 🔹 Récupérer tous les enseignants
     */
    @GetMapping
    public ResponseEntity<?> getAllTeachers() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, teacherService.getAllTeachers());
            output.put(MESSAGE, "Liste des enseignants récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Ajouter un enseignant
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> saveTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        log.info("Ajout d'un enseignant: {}", teacherDto);
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, teacherService.createTeacher(teacherDto));
            output.put(MESSAGE, "Enseignant ajouté avec succès");
            output.put(STATUS, 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Mettre à jour un enseignant
     */
    @PutMapping("/{teacherId}")
    public ResponseEntity<?> updateTeacher(@PathVariable("teacherId") Long teacherId, @Valid @RequestBody TeacherDto teacherDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, teacherService.updateTeacher(teacherId, teacherDto));
            output.put(MESSAGE, "Enseignant mis à jour avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Supprimer un enseignant
     */
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, teacherService.deleteTeacher(teacherId));
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}

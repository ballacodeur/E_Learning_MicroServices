package com.examen.e_learning.User_Service.users.user.controller;


import com.examen.e_learning.User_Service.users.user.dto.StudentDto;
import com.examen.e_learning.User_Service.users.user.service.StudentService;
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
@RequestMapping("/api/students")
@AllArgsConstructor
@Slf4j
public class StudentController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable("studentId") Long studentId) {
        Map<String, Object> output = new HashMap<>();
        try {
            Optional<StudentDto> student = studentService.getStudent(studentId);
            if (student.isPresent()) {
                output.put(RESULT, student.get());
                output.put(MESSAGE, "Étudiant trouvé avec succès");
                output.put(STATUS, 200);
                return ResponseEntity.ok(output);
            } else {
                throw new NotFoundException("Étudiant introuvable");
            }
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, studentService.getAllStudents());
            output.put(MESSAGE, "Liste des étudiants récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

//    @PostMapping
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public ResponseEntity<?> saveStudent( @RequestBody StudentDto studentDto) {
//        log.info("Ajout d'un étudiant: {}", studentDto);
//        Map<String, Object> output = new HashMap<>();
//        try {
//            output.put(RESULT, studentService.createStudent(studentDto));
//            output.put(MESSAGE, "Étudiant ajouté avec succès");
//            output.put(STATUS, 201);
//            return ResponseEntity.status(HttpStatus.CREATED).body(output);
//        } catch (Exception e) {
//            output.put(MESSAGE, e.getMessage());
//            output.put(STATUS, 500);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
//        }
//    }
@PostMapping
@ResponseStatus(code = HttpStatus.CREATED)
public ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto) {
    log.info("Requête reçue pour ajouter un étudiant: {}", studentDto);
    Map<String, Object> output = new HashMap<>();
    try {
        StudentDto savedStudent = studentService.createStudent(studentDto);
        output.put(RESULT, savedStudent);
        output.put(MESSAGE, "Étudiant ajouté avec succès");
        output.put(STATUS, 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    } catch (Exception e) {
        log.error("Erreur lors de l'ajout de l'étudiant", e);
        output.put(MESSAGE, e.getMessage());
        output.put(STATUS, 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
    }
}


    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") Long studentId, @Valid @RequestBody StudentDto studentDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, studentService.updateStudent(studentId, studentDto));
            output.put(MESSAGE, "Étudiant mis à jour avec succès");
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

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long studentId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, studentService.deleteStudent(studentId));
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

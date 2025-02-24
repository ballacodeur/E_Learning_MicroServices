package com.examn_elearning.courses_service.controller;

import com.examn_elearning.courses_service.dto.CourseDto;
import com.examn_elearning.courses_service.service.CourseService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    private static final String MESSAGE = "message";

    private static final String RESULT = "data";
    private static final String STATUS = "responseCode";
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        Map<String, Object> output = new HashMap<>();
        output.put("data", courseService.getAllCourses());
        output.put("message", "Liste des cours récupérée avec succès");
        output.put("status", 200);
        return ResponseEntity.ok(output);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
        Map<String, Object> output = new HashMap<>();
        output.put("data", courseService.saveCourse(courseDto));
        output.put("message", "Cours ajouté avec succès");
        output.put("status", 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") String courseId, @Valid @RequestBody CourseDto courseDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, courseService.updateCourse(courseId, courseDto));
            output.put(MESSAGE, "Course mis à jour avec succès");
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

//    @PutMapping("/{clientId}")
//    public ResponseEntity<?> updateClient(@PathVariable("clientId") String clientId, @RequestBody ClientDto updateClientDto) {
//        Map<String, Object> output = new HashMap<>();
//        try {
//            output.put(MESSAGE, clientService.updateClient(clientId, updateClientDto));
//            output.put(STATUS, 200);
//            return ResponseEntity.ok(output);
//        } catch (EntityNotFoundException e) {
//            output.put(MESSAGE, e.getMessage());
//            output.put(STATUS, 404);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
//        } catch (Exception e) {
//            output.put(MESSAGE, e.getMessage());
//            output.put(STATUS, 500);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
//        }
//    }

    @DeleteMapping("/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID courseID) {
        Map<String, Object> output = new HashMap<>();
        output.put("message", courseService.deleteCourse(courseID));
        output.put("status", 200);
        return ResponseEntity.ok(output);
    }
}


package com.examen.e_learning.User_Service.users.user.controller;


import com.examen.e_learning.User_Service.users.user.dto.UserDto;
import com.examen.e_learning.User_Service.users.user.service.UserService;
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
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final UserService userService;

    /**
     * 🔹 Récupérer un utilisateur par son ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        Map<String, Object> output = new HashMap<>();
        try {
            Optional<UserDto> user = userService.getUser(userId);
            if (user.isPresent()) {
                output.put(RESULT, user.get());
                output.put(MESSAGE, "Utilisateur trouvé avec succès");
                output.put(STATUS, 200);
                return ResponseEntity.ok(output);
            } else {
                throw new NotFoundException("Utilisateur introuvable");
            }
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    /**
     * 🔹 Récupérer la liste de tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, userService.getAllUsers());
            output.put(MESSAGE, "Liste des utilisateurs récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Créer un utilisateur (Student, Teacher, Admin)
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("res:"+ userDto.toString());
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, userService.createUser(userDto));
            output.put(MESSAGE, "Utilisateur ajouté avec succès");
            output.put(STATUS, 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    /**
     * 🔹 Mettre à jour un utilisateur
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDto userDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, userService.updateUser(userId, userDto));
            output.put(MESSAGE, "Utilisateur mis à jour avec succès");
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
     * 🔹 Supprimer un utilisateur
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, userService.deleteUser(userId));
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

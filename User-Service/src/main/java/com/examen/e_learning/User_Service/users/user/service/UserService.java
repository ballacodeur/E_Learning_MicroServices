package com.examen.e_learning.User_Service.users.user.service;

import com.examen.e_learning.User_Service.users.user.dto.StudentDto;
import com.examen.e_learning.User_Service.users.user.dto.UserDto;
import com.examen.e_learning.User_Service.users.user.entities.Student;
import com.examen.e_learning.User_Service.users.user.mapper.UserMapper;
import com.examen.e_learning.User_Service.users.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    /**
     * 🔹 Récupérer tous les utilisateurs
     */
    public List<UserDto> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des utilisateurs", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des utilisateurs");
        }
    }

    /**
     * 🔹 Récupérer un utilisateur par son ID
     */
    public Optional<UserDto> getUser(Long userId) {
        try {
            return Optional.of(userMapper.toDto(userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"))));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Utilisateur introuvable");
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'utilisateur avec l'ID {}", userId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'utilisateur");
        }
    }

    /**
     * 🔹 Enregistrer un nouvel utilisateur
     */
    public UserDto createUser(UserDto userDto) {
        try {
            return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de l'utilisateur", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de l'utilisateur");
        }
    }

    /**
     * 🔹 Mettre à jour un utilisateur
     */
    public String updateUser(Long userId, UserDto updateUser) {
        try {
            UserDto existingUser = getUser(userId).orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
            updateUser.setId(existingUser.getId());
            userRepository.save(userMapper.toEntity(updateUser));
            return "Utilisateur mis à jour avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'utilisateur avec l'ID {}", userId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour de l'utilisateur");
        }
    }

    /**
     * 🔹 Supprimer un utilisateur
     */
    public String deleteUser(Long userId) {
        try {
            UserDto existingUser = getUser(userId).orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
            userRepository.delete(userMapper.toEntity(existingUser));
            return "Utilisateur supprimé avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'utilisateur avec l'ID {}", userId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de l'utilisateur");
        }
    }
}

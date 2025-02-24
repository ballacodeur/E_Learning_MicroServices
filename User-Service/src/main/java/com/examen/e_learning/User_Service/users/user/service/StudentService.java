package com.examen.e_learning.User_Service.users.user.service;


import com.examen.e_learning.User_Service.helper.Helper;
import com.examen.e_learning.User_Service.users.user.dto.StudentDto;
import com.examen.e_learning.User_Service.users.user.dto.UserDto;
import com.examen.e_learning.User_Service.users.user.entities.Student;
import com.examen.e_learning.User_Service.users.user.mapper.UserMapper;
import com.examen.e_learning.User_Service.users.user.repository.StudentRepository;
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
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserMapper userMapper;
    private final KeycloakHelperService keycloakHelperService;

    public StudentService(KeycloakHelperService keycloakHelperService,StudentRepository studentRepository, UserMapper userMapper) {
        this.studentRepository = studentRepository;
        this.userMapper = userMapper;
        this.keycloakHelperService = keycloakHelperService;
    }

    public List<StudentDto> getAllStudents() {
        try {
            return studentRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des étudiants", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des étudiants");
        }
    }

    public Optional<StudentDto> getStudent(Long studentId) {
        try {
            return Optional.of(userMapper.toDto(studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Étudiant introuvable"))));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'étudiant avec l'ID {}", studentId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'étudiant");
        }
    }

//    public StudentDto createStudent(StudentDto studentDto) {
//        try {
//            log.info("ddddddddddddddddddd:"+studentDto.toString());
//            UserDto userDto = new UserDto();
//            userDto.setFirstName(studentDto.getFirstName());
//            userDto.setLastName(studentDto.getLastName());
//            userDto.setEmailPro(studentDto.getEmailPro());
//            userDto.setPassword(studentDto.getPassword());
//            keycloakHelperService.handleUser(userDto);
//            studentDto.setPassword(Helper.hashPassword(studentDto.getPassword()));
//            return userMapper.toDto(studentRepository.save(userMapper.toEntity(studentDto)));
//        } catch (Exception e) {
//            log.error("Erreur lors de l'enregistrement de l'étudiant", e);
//            throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de l'étudiant");
//        }
//    }
//public StudentDto createStudent(StudentDto studentDto) {
//    try {
//        log.info("Données reçues pour ajout d'un étudiant: {}", studentDto.toString());
//
//        // Vérification des champs obligatoires
//        if (studentDto.getEmailPro() == null || studentDto.getEmailPro().isBlank()) {
//            throw new IllegalArgumentException("L'email est obligatoire.");
//        }
//        if (studentDto.getPhoneNumber() == null || studentDto.getPhoneNumber().isBlank()) {
//            throw new IllegalArgumentException("Le numéro de téléphone est obligatoire.");
//        }
//        if (studentDto.getPassword() == null || studentDto.getPassword().isBlank()) {
//            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
//        }
//
//        // Création d'un `UserDto` pour Keycloak
//        UserDto userDto = new UserDto();
//        userDto.setFirstName(studentDto.getFirstName());
//        userDto.setLastName(studentDto.getLastName());
//        userDto.setEmailPro(studentDto.getEmailPro());
//        userDto.setPhoneNumber(studentDto.getPhoneNumber()); // Ajout de phoneNumber
//        userDto.setPassword(studentDto.getPassword());
//
//        // Ajout dans Keycloak
//        keycloakHelperService.handleUser(userDto);
//
//        // Hashage du mot de passe avant enregistrement
//        studentDto.setPassword(Helper.hashPassword(studentDto.getPassword()));
//
//        // Conversion en entité et sauvegarde
//        Student student = userMapper.toEntity(studentDto);
//        student = studentRepository.save(student);
//
//        return userMapper.toDto(student);
//    } catch (IllegalArgumentException e) {
//        log.error("Données invalides: {}", e.getMessage());
//        throw new InternalServerErrorException(e.getMessage());
//    } catch (Exception e) {
//        log.error("Erreur lors de l'enregistrement de l'étudiant", e);
//        throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de l'étudiant.");
//    }
//}

    public StudentDto createStudent(StudentDto studentDto) {
        try {
            log.info("Données reçues pour ajout d'un étudiant: {}", studentDto);
            studentDto.setRegistrationId(4L);
            studentDto.setClassId(3L);
            studentDto.setAddress("dd");
            studentDto.setPassword("ppp");
            studentDto.setEmailPro("pp@gmai.com");
            studentDto.setProfileType("STUDENT");
            studentDto.setFirstName("ppp");
            studentDto.setLastName("ppp");
            studentDto.setPhoneNumber("777777777");

            // ✅ Vérification des champs obligatoires
            if (studentDto.getEmailPro() == null || studentDto.getEmailPro().isBlank()) {
                throw new IllegalArgumentException("L'email est obligatoire.");
            }
            if (studentDto.getPhoneNumber() == null || studentDto.getPhoneNumber().isBlank()) {
                throw new IllegalArgumentException("Le numéro de téléphone est obligatoire.");
            }
            if (studentDto.getPassword() == null || studentDto.getPassword().isBlank()) {
                throw new IllegalArgumentException("Le mot de passe est obligatoire.");
            }

            // ✅ Création d'un `UserDto` pour Keycloak
            UserDto userDto = new UserDto();
            userDto.setFirstName(studentDto.getFirstName());
            userDto.setLastName(studentDto.getLastName());
            userDto.setEmailPro(studentDto.getEmailPro());
            userDto.setPhoneNumber(studentDto.getPhoneNumber());
            userDto.setPassword(studentDto.getPassword());

            // ✅ Ajout dans Keycloak
            keycloakHelperService.handleUser(userDto);

            // ✅ Hashage du mot de passe avant enregistrement
            studentDto.setPassword(Helper.hashPassword(studentDto.getPassword()));

            // ✅ Conversion en entité et sauvegarde
            Student student = userMapper.toEntity(studentDto);
            student = studentRepository.save(student);

            return userMapper.toDto(student);
        } catch (IllegalArgumentException e) {
            log.error("Données invalides: {}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de l'étudiant", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de l'étudiant.");
        }
    }



    public String updateStudent(Long studentId, StudentDto updateStudent) {
        try {
            StudentDto existingStudent = getStudent(studentId).orElseThrow(() -> new EntityNotFoundException("Étudiant introuvable"));
            updateStudent.setId(existingStudent.getId());
            studentRepository.save(userMapper.toEntity(updateStudent));
            return "Étudiant mis à jour avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'étudiant avec l'ID {}", studentId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour de l'étudiant");
        }
    }

    public String deleteStudent(Long studentId) {
        try {
            StudentDto existingStudent = getStudent(studentId).orElseThrow(() -> new EntityNotFoundException("Étudiant introuvable"));
            studentRepository.delete(userMapper.toEntity(existingStudent));
            return "Étudiant supprimé avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'étudiant avec l'ID {}", studentId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de l'étudiant");
        }
    }
}


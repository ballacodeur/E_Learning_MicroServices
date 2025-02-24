package com.examen.e_learning.User_Service.users.user.service;

import com.examen.e_learning.User_Service.users.user.dto.TeacherDto;
import com.examen.e_learning.User_Service.users.user.entities.Teacher;
import com.examen.e_learning.User_Service.users.user.mapper.UserMapper;
import com.examen.e_learning.User_Service.users.user.repository.TeacherRepository;
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
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserMapper userMapper;

    public TeacherService(TeacherRepository teacherRepository, UserMapper userMapper) {
        this.teacherRepository = teacherRepository;
        this.userMapper = userMapper;
    }

    public List<TeacherDto> getAllTeachers() {
        try {
            return teacherRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des enseignants", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des enseignants");
        }
    }

    public Optional<TeacherDto> getTeacher(Long teacherId) {
        try {
            return Optional.of(userMapper.toDto(teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable"))));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'enseignant avec l'ID {}", teacherId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'enseignant");
        }
    }

    public TeacherDto createTeacher(TeacherDto teacherDto) {
        try {
            return userMapper.toDto(teacherRepository.save(userMapper.toEntity(teacherDto)));
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de l'enseignant", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de l'enseignant");
        }
    }

    public String updateTeacher(Long teacherId, TeacherDto updateTeacher) {
        try {
            TeacherDto existingTeacher = getTeacher(teacherId).orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable"));
            updateTeacher.setId(existingTeacher.getId());
            teacherRepository.save(userMapper.toEntity(updateTeacher));
            return "Enseignant mis à jour avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'enseignant avec l'ID {}", teacherId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour de l'enseignant");
        }
    }

    public String deleteTeacher(Long teacherId) {
        try {
            TeacherDto existingTeacher = getTeacher(teacherId).orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable"));
            teacherRepository.delete(userMapper.toEntity(existingTeacher));
            return "Enseignant supprimé avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'enseignant avec l'ID {}", teacherId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de l'enseignant");
        }
    }
}


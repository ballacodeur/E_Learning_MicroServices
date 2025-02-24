package com.examn_elearning.courses_service.service;

import com.examn_elearning.courses_service.dto.CourseDto;
import com.examn_elearning.courses_service.entities.Course;
import com.examn_elearning.courses_service.mapper.CourseMapper;
import com.examn_elearning.courses_service.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDto> getAllCourses() {
        try {
            return courseRepository.findAll().stream()
                    .map(courseMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des cours", e);
            throw new InternalServerErrorException("Erreur lors de la récupération des cours");
        }
    }

    public CourseDto getCourseById(UUID courseID) {
        try {
            return courseMapper.toDto(courseRepository.findById(String.valueOf(courseID))
                    .orElseThrow(() -> new EntityNotFoundException("Cours introuvable")));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du cours avec ID {}", courseID, e);
            throw new InternalServerErrorException("Erreur lors de la récupération du cours");
        }
    }

    public CourseDto saveCourse(CourseDto courseDto) {
        try {
            return courseMapper.toDto(courseRepository.save(courseMapper.toEntity(courseDto)));
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement du cours", e);
            throw new InternalServerErrorException("Erreur lors de l'enregistrement du cours");
        }
    }

    public String deleteCourse(UUID courseID) {
        try {
            CourseDto existingCourse = getCourseById(courseID);
            courseRepository.delete(courseMapper.toEntity(existingCourse));
            return "Cours supprimé avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la suppression du cours avec ID {}", courseID, e);
            throw new InternalServerErrorException("Erreur lors de la suppression du cours");
        }
    }

    public String updateCourse(String courseID, CourseDto updateCourse) {
        try {
            log.debug("Tentative de mise à jour du cours avec l'ID {}", courseID);

            CourseDto existingUser = getCourseById(UUID.fromString(courseID));
            updateCourse.setCourseID(existingUser.getCourseID());
            courseRepository.save(courseMapper.toEntity(updateCourse));
            return "Utilisateur mis à jour avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'utilisateur avec l'ID {}", courseID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour de l'utilisateur");
        }
    }
}




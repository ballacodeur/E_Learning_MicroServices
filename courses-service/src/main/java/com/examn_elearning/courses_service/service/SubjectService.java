package com.examn_elearning.courses_service.service;


import com.examn_elearning.courses_service.dto.SubjectDto;
import com.examn_elearning.courses_service.mapper.SubjectMapper;
import com.examn_elearning.courses_service.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public List<SubjectDto> getAllSubjects() {
        try {
            return subjectRepository.findAll().stream()
                    .map(subjectMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des matières", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des matières");
        }
    }

    public SubjectDto getSubjectById(UUID subjectID) {
        try {
            return subjectMapper.toDto(subjectRepository.findById(String.valueOf(subjectID))
                    .orElseThrow(() -> new EntityNotFoundException("Matière introuvable")));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la matière avec ID {}", subjectID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de la matière");
        }
    }

    public SubjectDto saveSubject(SubjectDto subjectDto) {
        try {
            return subjectMapper.toDto(subjectRepository.save(subjectMapper.toEntity(subjectDto)));
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de la matière", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de l'enregistrement de la matière");
        }
    }

    public String deleteSubject(UUID subjectID) {
        try {
            SubjectDto existingSubject = getSubjectById(subjectID);
            subjectRepository.delete(subjectMapper.toEntity(existingSubject));
            return "Matière supprimée avec succès";
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la matière avec ID {}", subjectID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de la matière");
        }
    }
}


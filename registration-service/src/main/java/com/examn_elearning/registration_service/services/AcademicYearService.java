package com.examn_elearning.registration_service.services;

import com.examn_elearning.registration_service.dto.AcademicYearDto;
import com.examn_elearning.registration_service.mapper.AcademicYearMapper;
import com.examn_elearning.registration_service.repository.AcademicYearRepository;
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
public class AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;

    public AcademicYearService(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper, AcademicYearMapper academicYearMapper1) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
    }

    public List<AcademicYearDto> getAllAcademicYears() {
        try {
            return academicYearRepository.findAll().stream()
                    .map(academicYearMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des années académiques", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des années académiques");
        }
    }

    public AcademicYearDto getAcademicYearById(UUID academicYearID) {
        try {
            return academicYearMapper.toDto(academicYearRepository.findById(String.valueOf(academicYearID))
                    .orElseThrow(() -> new EntityNotFoundException("L'année académique est introuvable")));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Année Académique Introuvable");
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'année académique avec l'ID {}", academicYearID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'année académique");
        }
    }

    public AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto) {
        try {
            return academicYearMapper.toDto(academicYearRepository.save(academicYearMapper.toEntity(academicYearDto)));
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde de l'année académique", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la sauvegarde de l'année académique");
        }
    }
}


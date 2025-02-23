package com.examn_elearning.registration_service.services;


import com.examn_elearning.registration_service.dto.ClasseDto;
import com.examn_elearning.registration_service.mapper.ClasseMapper;
import com.examn_elearning.registration_service.repository.ClasseRepository;
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
public class ClasseService {
    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;

    public ClasseService(ClasseRepository classeRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    public List<ClasseDto> getAllClasses() {
        try {
            return classeRepository.findAll().stream()
                    .map(classeMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des classes", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des classes");
        }
    }

    public ClasseDto getClassById(UUID classID) {
        try {
            return classeMapper.toDto(classeRepository.findById(String.valueOf(classID))
                    .orElseThrow(() -> new EntityNotFoundException("La classe est introuvable")));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Classe Introuvable");
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la classe avec l'ID {}", classID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de la classe");
        }
    }

    public ClasseDto saveClass(ClasseDto classeDto) {
        try {
            return classeMapper.toDto(classeRepository.save(classeMapper.toEntity(classeDto)));
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde de la classe", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la sauvegarde de la classe");
        }
    }

    public String deleteClass(UUID classID) {
        try {
            ClasseDto existingClass = getClassById(classID);
            classeRepository.delete(classeMapper.toEntity(existingClass));
            return "Classe supprimée avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la classe avec l'ID {}", classID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de la classe");
        }
    }
}

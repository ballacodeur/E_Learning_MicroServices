package com.examn_elearning.registration_service.services;


import com.examn_elearning.registration_service.dto.RegistrationDto;
import com.examn_elearning.registration_service.mapper.RegistrationMapper;
import com.examn_elearning.registration_service.repository.RegistrationRepository;
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
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    public RegistrationService(RegistrationRepository registrationRepository, RegistrationMapper registrationMapper) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
    }

    public List<RegistrationDto> getAllRegistrations() {
        try {
            return registrationRepository.findAll().stream()
                    .map(registrationMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des inscriptions", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des inscriptions");
        }
    }

    public RegistrationDto getRegistrationById(UUID registrationID) {
        try {
            return registrationMapper.toDto(registrationRepository.findById(String.valueOf(registrationID))
                    .orElseThrow(() -> new EntityNotFoundException("L'inscription est introuvable")));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Inscription Introuvable");
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'inscription avec l'ID {}", registrationID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'inscription");
        }
    }

    public RegistrationDto saveRegistration(RegistrationDto registrationDto) {
        try {
            return registrationMapper.toDto(registrationRepository.save(registrationMapper.toEntity(registrationDto)));
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde de l'inscription", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la sauvegarde de l'inscription");
        }
    }

    public String deleteRegistration(UUID registrationID) {
        try {
            RegistrationDto existingRegistration = getRegistrationById(registrationID);
            registrationRepository.delete(registrationMapper.toEntity(existingRegistration));
            return "Inscription supprimée avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'inscription avec l'ID {}", registrationID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression de l'inscription");
        }
    }
}

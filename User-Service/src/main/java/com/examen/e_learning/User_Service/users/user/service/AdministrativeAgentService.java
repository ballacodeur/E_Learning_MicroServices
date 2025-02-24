package com.examen.e_learning.User_Service.users.user.service;

import com.examen.e_learning.User_Service.users.user.dto.AdministrativeDto;
import com.examen.e_learning.User_Service.users.user.entities.AdministrativeAgent;
import com.examen.e_learning.User_Service.users.user.mapper.UserMapper;
import com.examen.e_learning.User_Service.users.user.repository.AdministrativeAgentRepository;
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
public class AdministrativeAgentService {
    private final AdministrativeAgentRepository administrativeAgentRepository;
    private final UserMapper userMapper;

    public AdministrativeAgentService(AdministrativeAgentRepository administrativeAgentRepository, UserMapper userMapper) {
        this.administrativeAgentRepository = administrativeAgentRepository;
        this.userMapper = userMapper;
    }

    public List<AdministrativeDto> getAllAdministrativeAgents() {
        try {
            return administrativeAgentRepository.findAll().stream()
                    .map(userMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des agents administratifs", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des agents administratifs");
        }
    }

    public Optional<AdministrativeDto> getAdministrativeAgent(Long agentId) {
        try {
            return Optional.of(userMapper.toDto(administrativeAgentRepository.findById(String.valueOf(agentId))
                    .orElseThrow(() -> new EntityNotFoundException("Agent administratif introuvable"))));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'agent administratif avec l'ID {}", agentId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération de l'agent administratif");
        }
    }
}

package jamonodev.clientsservices.services;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jamonodev.clientsservices.dao.ClientRepository;
import jamonodev.clientsservices.dto.ClientDto;
import jamonodev.clientsservices.exception.EntityNotFoundException;
import jamonodev.clientsservices.mapping.ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<ClientDto> getAllClientsBySites( String siteID) {
        try {
            return getAllClients().stream()
                    .filter(c -> c.getSiteID().equalsIgnoreCase(siteID)).toList();
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération des clients", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des clients");
        }
    }

    public List<ClientDto> getAllClients() {
        try {
            return clientRepository.findAll().stream()
                    .map(clientMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération des clients", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération des clients");
        }
    }

    public ClientDto getClientById(UUID clientID) {
        try {
            return clientMapper.toDto(clientRepository.findById(clientID)
                    .orElseThrow(() -> new EntityNotFoundException("Le client est introuvable")));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Client Introuvable");
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la récupération du client avec l'ID {}", clientID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la récupération du client");
        }
    }

    public ClientDto saveClient(ClientDto clientDto) {
        try {
            return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto)));
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la sauvegarde du client", e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la sauvegarde du client");
        }
    }

    public String updateClient(String clientID, ClientDto updateClient) {
        try {
            ClientDto existingClient = getClientById(UUID.fromString(clientID));
            updateClient.setClientID(existingClient.getClientID());
            clientRepository.save(clientMapper.toEntity(updateClient));
            return "Modifications effectuées avec succès";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la mise à jour du client avec l'ID {}", clientID, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la mise à jour du client");
        }
    }

    public String deleteClient(String clientId) {
        try {
            ClientDto existingClient = getClientById(UUID.fromString(clientId));
            clientRepository.delete(clientMapper.toEntity(existingClient));
            return "Le client a été bien supprimé";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Une erreur est survenue lors de la suppression du client avec l'ID {}", clientId, e);
            throw new InternalServerErrorException("Une erreur est survenue lors de la suppression du client");
        }
    }
}

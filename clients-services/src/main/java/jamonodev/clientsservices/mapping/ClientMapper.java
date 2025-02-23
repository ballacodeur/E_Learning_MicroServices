package jamonodev.clientsservices.mapping;

import jamonodev.clientsservices.dto.ClientDto;
import jamonodev.clientsservices.entities.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);
}

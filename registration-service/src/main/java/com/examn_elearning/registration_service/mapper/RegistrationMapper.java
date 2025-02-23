package com.examn_elearning.registration_service.mapper;

import com.examn_elearning.registration_service.dto.RegistrationDto;
import com.examn_elearning.registration_service.entites.Registrations;
import org.mapstruct.Mapper;

@Mapper
public interface RegistrationMapper {
    RegistrationDto toDto(Registrations registrations);
    Registrations toEntity(RegistrationDto registrationDto);
}

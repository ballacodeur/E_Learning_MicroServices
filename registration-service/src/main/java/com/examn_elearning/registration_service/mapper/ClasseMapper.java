package com.examn_elearning.registration_service.mapper;

import com.examn_elearning.registration_service.dto.ClasseDto;
import com.examn_elearning.registration_service.entites.Classe;
import org.mapstruct.Mapper;

@Mapper
public interface ClasseMapper {

    ClasseDto toDto(Classe classe);

    Classe toEntity(ClasseDto classeDto);
}

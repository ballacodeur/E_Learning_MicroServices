package com.examn_elearning.registration_service.mapper;

import com.examn_elearning.registration_service.dto.AcademicYearDto;
import com.examn_elearning.registration_service.entites.AcademicYear;
import org.mapstruct.Mapper;

@Mapper
public interface AcademicYearMapper {

    AcademicYearDto toDto(AcademicYear academicYear);

    AcademicYear toEntity(AcademicYearDto academicYearDto);
}

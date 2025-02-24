package com.examn_elearning.courses_service.mapper;

import com.examn_elearning.courses_service.dto.SubjectDto;
import com.examn_elearning.courses_service.entities.Subject;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {
    SubjectDto toDto(Subject subject);

    Subject toEntity(SubjectDto subjectDto);
}

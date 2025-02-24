package com.examn_elearning.courses_service.mapper;

import com.examn_elearning.courses_service.dto.CourseDto;
import com.examn_elearning.courses_service.entities.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {
    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
}

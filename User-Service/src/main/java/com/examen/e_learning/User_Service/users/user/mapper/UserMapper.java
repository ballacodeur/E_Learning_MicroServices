package com.examen.e_learning.User_Service.users.user.mapper;

import com.examen.e_learning.User_Service.users.user.dto.AdministrativeDto;
import com.examen.e_learning.User_Service.users.user.dto.StudentDto;
import com.examen.e_learning.User_Service.users.user.dto.TeacherDto;
import com.examen.e_learning.User_Service.users.user.dto.UserDto;
import com.examen.e_learning.User_Service.users.user.entities.AdministrativeAgent;
import com.examen.e_learning.User_Service.users.user.entities.Student;
import com.examen.e_learning.User_Service.users.user.entities.Teacher;
import com.examen.e_learning.User_Service.users.user.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity (UserDto userDto);

    TeacherDto toDto(Teacher teacher);

    Teacher toEntity (TeacherDto teacherDto);

    StudentDto toDto(Student student);

    Student toEntity (StudentDto studentDto);

    AdministrativeDto toDto(AdministrativeAgent administrativeAgent);

    AdministrativeAgent toEntity (AdministrativeDto administrativeDto);
}

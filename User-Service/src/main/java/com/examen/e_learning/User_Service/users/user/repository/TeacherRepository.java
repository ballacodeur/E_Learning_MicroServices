package com.examen.e_learning.User_Service.users.user.repository;

import com.examen.e_learning.User_Service.users.user.entities.Teacher;
import com.examen.e_learning.User_Service.users.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

}

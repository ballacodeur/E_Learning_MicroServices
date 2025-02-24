package com.examen.e_learning.User_Service.users.user.repository;

import com.examen.e_learning.User_Service.users.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.emailPro = :emailPro")
    Optional<User> findByEmail(@Param("emailPro") String emailPro);

}

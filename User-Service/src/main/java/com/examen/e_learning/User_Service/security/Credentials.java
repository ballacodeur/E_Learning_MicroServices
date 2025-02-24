package com.examen.e_learning.User_Service.security;

import com.examen.e_learning.User_Service.helper.Helper;
import com.examen.e_learning.User_Service.users.user.entities.User;
import com.examen.e_learning.User_Service.users.user.mapper.UserMapper;
import com.examen.e_learning.User_Service.users.user.repository.UserRepository;
import com.examen.e_learning.User_Service.users.user.service.KeycloakHelperService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class Credentials {
    private final KeycloakHelperService keycloakHelperService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Credentials(KeycloakHelperService keycloakHelperService, UserRepository userRepository, UserMapper userMapper) {
        this.keycloakHelperService = keycloakHelperService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }




    // ALL USERS
    public List<User> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getFirstName)
                        .thenComparing(User::getLastName))
                .collect(Collectors.toList());
    }

    // GET USER
    public User getUser(Long userID){
        return userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));
    }

    public User getUserConnected(){
        String userID = Helper.getCurrentUserId();
        assert userID != null;
        return userRepository.findById(Long.valueOf(userID))
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));
    }

    public String updatePassword(Long userID, String newPassword){
        User existingUser = getUser(userID);
        existingUser.setPassword(Helper.hashPassword(newPassword));
        userRepository.save(existingUser);
        keycloakHelperService.updatePassword(userID, newPassword);
        return "Mot de passe modifié avec succès ...";
    }
}

package com.examen.e_learning.User_Service.users.user.service;

import com.examen.e_learning.User_Service.exception.RequestException;
import com.examen.e_learning.User_Service.helper.Helper;
import com.examen.e_learning.User_Service.security.KeycloakInstance;
import com.examen.e_learning.User_Service.users.user.dto.UserDto;
import com.examen.e_learning.User_Service.users.user.entities.User;
import com.examen.e_learning.User_Service.users.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class KeycloakHelperService {
    @Value("${keycloak.realm}")
    private String realm;
    private final KeycloakInstance keycloakInstance;
    private final UserRepository utilisateurRepository;
    private final UserService userService;
    public KeycloakHelperService(UserService userService,KeycloakInstance keycloakInstance, UserRepository utilisateurRepository) {
        this.keycloakInstance = keycloakInstance;
        this.utilisateurRepository = utilisateurRepository;
        this.userService = userService;
    }

    private Keycloak getKeycloak() {
        return keycloakInstance.getKeycloakInstance();
    }

    private RealmResource getRealmResource() {
        return getKeycloak().realm(realm);
    }

    private UsersResource getUsersResource() {
        return getRealmResource().users();
    }

    public UserDto handleUser(UserDto userDto){
//        UserDto profileDto = userService.getUser(userDto.getId()).get();
//        ifEmailIsUsed(userDto.getEmail());
//        ifNomUtilisateurIsUsed(utilisateurDto.getNomUtilisateur());
        String utilisateurID = saveUserInKeycloak(userDto);
        userDto.setProfileType(userDto.getProfileType());
        userDto.setId(userDto.getId());
        userDto.setPassword(Helper.hashPassword(userDto.getPassword()));
        return userDto;
    }

//    private String saveUserInKeycloak(UserDto userDto){
//        try {
//            validateEmail(userDto.getEmailPro());
//            validatePhoneNumber(userDto.getPhoneNumber());
//            UserRepresentation user = toUserRepresentation(userDto);
//            getUsersResource().create(user);
//            return findUserByUsername(userDto.getEmailPro()).getId();
//        } catch(WebApplicationException e) {
//            if (e.getResponse().getStatus() == 409) {
//                throw new RuntimeException("Cet utilisateur existe déjà !");
//            } else {
//                throw new RuntimeException("Une erreur est survenue lors de la création de l'utilisateur !");
//            }
//        } catch (Exception e){
//            log.info(e.toString());
//            Throwable cause = e.getCause();
//            if (cause instanceof NotAuthorizedException) {
//                throw new RuntimeException("Accès non autorisé !");
//            } else {
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//    }

    private String saveUserInKeycloak(UserDto userDto) {
        try {
            log.info("Vérification de l'email et du numéro de téléphone...");
            validateEmail(userDto.getEmailPro());
            validatePhoneNumber(userDto.getPhoneNumber());

            log.info("Création de UserRepresentation...");
            UserRepresentation user = toUserRepresentation(userDto);

            log.info("Envoi de la requête à Keycloak...");
            getUsersResource().create(user);

            log.info("Utilisateur créé avec succès !");
            return findUserByUsername(userDto.getEmailPro()).getId();
        } catch (WebApplicationException e) {
            log.error("Erreur Keycloak : {}", e.getResponse().readEntity(String.class));
            if (e.getResponse().getStatus() == 409) {
                throw new RuntimeException("Cet utilisateur existe déjà dans Keycloak !");
            } else if (e.getResponse().getStatus() == 401) {
                throw new RuntimeException("Accès non autorisé à Keycloak !");
            } else {
                throw new RuntimeException("Erreur Keycloak: " + e.getResponse().getStatus());
            }
        } catch (Exception e) {
            log.error("Erreur inconnue lors de la création de l'utilisateur dans Keycloak", e);
            throw new RuntimeException("Erreur inconnue lors de la création de l'utilisateur dans Keycloak", e);
        }
    }


    private UserRepresentation toUserRepresentation(UserDto utilisateurDto) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(utilisateurDto.getEmailPro());
        user.setFirstName(utilisateurDto.getFirstName());
        user.setLastName(utilisateurDto.getLastName());
        user.setEmail(utilisateurDto.getEmailPro());
        user.setEnabled(true);
        user.setCredentials(Arrays.asList(createPasswordCredential(utilisateurDto.getPassword())));
        return user;
    }

    private CredentialRepresentation createPasswordCredential(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public void deleteUserKeycloak(String userID){
        getUsersResource().get(userID).remove();
    }
    private UserRepresentation findUserByUsername(String username) {
        return getUsersResource().list().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable !"));
    }

    public void updateUserKeycloak(String userId, UserDto updatedUser) {
        validateEmail(updatedUser.getEmailPro());
        validatePhoneNumber(updatedUser.getPhoneNumber());
        UserRepresentation existingUser = getUsersResource().get(userId).toRepresentation();
        updateUserRepresentation(existingUser, updatedUser);
        getUsersResource().get(userId).update(existingUser);
    }
    private void updateUserRepresentation(UserRepresentation existingUser, UserDto updatedUser) {
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmailPro());
        existingUser.setUsername(updatedUser.getEmailPro());
        existingUser.setEnabled(true);
    }

    public void validateEmail(String email) {
        if (!Helper.isValidEmail(email)) {
            throw new RuntimeException("Adresse e-mail invalide !");
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (!Helper.isValidSenegalPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Le numéro de téléphone n'est pas valide pour le Sénégal !");
        }
    }

    private void ifEmailIsUsed(String email){
        Optional<User> user = utilisateurRepository.findByEmail(email);
        if (user.isPresent()){
            throw new RuntimeException("Cette adresse e-mail est déjà utilisée !");
        }
    }

    private void ifNomUtilisateurIsUsed(String email){
        Optional<User> user = utilisateurRepository.findByEmail(email);
        if (user.isPresent()){
            throw new RequestException("Ce nom utilisateur est déjà utilisé !", HttpStatus.BAD_REQUEST);
        }
    }

    public void updatePassword(Long userId, String newPassword) {
        UserRepresentation userRepresentation = getUsersResource().get(String.valueOf(userId)).toRepresentation();
        if (userRepresentation == null) {
            throw new RuntimeException("Utilisateur introuvable !");
        }
        CredentialRepresentation newCredential = createPasswordCredential(newPassword);
        getUsersResource().get(String.valueOf(userId)).resetPassword(newCredential);
    }
}
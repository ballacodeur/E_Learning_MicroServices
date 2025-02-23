package jamono.authkeyclock.services;


import jamono.authkeyclock.request.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService {
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.tokenUrl}")
    private String keycloakTokenUrl;
    @Value("${keycloak.logout-url}")
    private String logoutUrl;
    @Autowired
    private RestTemplate restTemplate;

    // LOGIN
    public String login(AuthRequest authRequest) {
        Map<String, Object> output = new HashMap<>();
        try {
            // token
            return authenticate(authRequest);
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String authenticate(AuthRequest authRequest) {
        log.info("clientID => {}", clientId);
        log.info("clientSecret => {}", clientSecret);
        log.info("tokenURL => {}", keycloakTokenUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = createRequestBody(authRequest);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(keycloakTokenUrl, requestEntity, Map.class);
            return (String) response.getBody().get("access_token");
        } catch (ResourceAccessException ex) {
            log.info("Erreur => {}", ex.toString());
            throw new RuntimeException(ex.getMessage());
        } catch (RestClientException ex) {
            log.info("Erreur => {}", ex.toString());
            throw new  RuntimeException(ex.getMessage());
        }
    }

    private MultiValueMap<String, String> createRequestBody(AuthRequest authRequest) {
        log.info(authRequest.toString());
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", authRequest.getUsername());
        requestBody.add("password", authRequest.getPassword());
        return requestBody;
    }

    // LOGOUT
    public String logout(String accessToken) {
        return "";}
}
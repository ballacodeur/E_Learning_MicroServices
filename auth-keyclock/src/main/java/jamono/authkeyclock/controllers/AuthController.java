package jamono.authkeyclock.controllers;



import jamono.authkeyclock.request.AuthRequest;
import jamono.authkeyclock.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth-service/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        Map<String, Object> output = new HashMap<>();
        try {
            output.put("access_token", authService.login(authRequest));
            output.put("message", "Authentification réussie avec succès.");
            output.put("responseCode", 200);
            return ResponseEntity.ok(output);
        } catch (HttpClientErrorException e){
            throw new RuntimeException(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
  }
}
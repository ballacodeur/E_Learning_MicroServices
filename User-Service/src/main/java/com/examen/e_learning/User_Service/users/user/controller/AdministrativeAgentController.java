package com.examen.e_learning.User_Service.users.user.controller;

import com.examen.e_learning.User_Service.users.user.dto.AdministrativeDto;
import com.examen.e_learning.User_Service.users.user.service.AdministrativeAgentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/administrative-agents")
@AllArgsConstructor
@Slf4j
public class AdministrativeAgentController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";

    private final AdministrativeAgentService administrativeAgentService;

    @GetMapping("/{agentId}")
    public ResponseEntity<?> getAdministrativeAgentById(@PathVariable("agentId") Long agentId) {
        Map<String, Object> output = new HashMap<>();
        try {
            Optional<AdministrativeDto> agent = administrativeAgentService.getAdministrativeAgent(agentId);
            if (agent.isPresent()) {
                output.put(RESULT, agent.get());
                output.put(MESSAGE, "Agent administratif trouvé avec succès");
                output.put(STATUS, 200);
                return ResponseEntity.ok(output);
            } else {
                throw new NotFoundException("Agent administratif introuvable");
            }
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }
}

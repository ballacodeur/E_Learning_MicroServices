package jamonodev.clientsservices.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jamonodev.clientsservices.dto.ClientDto;
import jamonodev.clientsservices.exception.EntityNotFoundException;
import jamonodev.clientsservices.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {
    private static final String RESULT = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "responseCode";
    private final ClientService clientService;


    @GetMapping("/site/{siteID}/clients")
    public ResponseEntity<?> getAllClientsBySite(@PathVariable String siteID) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, clientService.getAllClientsBySites(siteID));
            output.put(MESSAGE, "Liste des clients du site récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @GetMapping("/{clientID}")
    public ResponseEntity<?> getClientById(@PathVariable("clientID") String clientID) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, clientService.getClientById(UUID.fromString(clientID)));
            output.put(MESSAGE, "Client recherché trouvé avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, clientService.getAllClients());
            output.put(MESSAGE, "Liste des clients récupérée avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> saveClient(@Valid @RequestBody ClientDto clientDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(RESULT, clientService.saveClient(clientDto));
            output.put(MESSAGE, "Client ajouté avec succès");
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable("clientId") String clientId, @RequestBody ClientDto updateClientDto) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, clientService.updateClient(clientId, updateClientDto));
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (EntityNotFoundException e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }

    @DeleteMapping("/{clientID}")
    public ResponseEntity<?> deleteClient(@PathVariable("clientID") String clientId) {
        Map<String, Object> output = new HashMap<>();
        try {
            output.put(MESSAGE, clientService.deleteClient(clientId));
            output.put(STATUS, 200);
            return ResponseEntity.ok(output);
        } catch (NotFoundException e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(output);
        } catch (Exception e) {
            output.put(MESSAGE, e.getMessage());
            output.put(STATUS, 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
        }
    }
}

package com.orm.lab02.controller;

import com.orm.lab02.entity.Client;
import com.orm.lab02.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client createdClient = clientRepository.save(client);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdClient);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientRepository.findAll();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> optionalExistingClient = clientRepository.findById(id);

        if (optionalExistingClient.isPresent()) {
            Client existingClient = optionalExistingClient.get();

            existingClient.setClientName(client.getClientName());
            existingClient.setClientEmailAddress(client.getClientEmailAddress());
            existingClient.setClientPhoneNumber(client.getClientPhoneNumber());

            Client updatedClient = clientRepository.save(existingClient);

            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteClientById(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clientRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/mostOrders")
    public ResponseEntity<List<Client>> getClientsWithMostOrders() {
        List<Client> clients = clientRepository.getClientsWithMostOrders();
        return ResponseEntity.ok(clients);
    }
}

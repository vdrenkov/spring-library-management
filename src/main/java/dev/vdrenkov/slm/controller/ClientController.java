package dev.vdrenkov.slm.controller;

import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.entity.Client;
import dev.vdrenkov.slm.request.ClientRequest;
import dev.vdrenkov.slm.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Void> addClient(@RequestBody @Valid ClientRequest clientRequest) {
        Client client = clientService.addClient(clientRequest);

        URI location = UriComponentsBuilder.fromUriString("/clients/{id}").buildAndExpand(client.getId()).toUri();

        log.info("A new client added");
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        log.info("All clients requested from the database");
        return ResponseEntity.ok(clientService.getAllClientsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable int id) {
        log.info("Client with an ID {} requested from the database.", id);
        return ResponseEntity.ok(clientService.getClientDtoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable int id,
        @RequestParam(required = false) Boolean returnOld) {

        ClientDto clientDto = clientService.deleteClient(id);

        log.info("Client with an ID {} deleted from the database.", id);

        if (Boolean.TRUE.equals(returnOld)) {
            return ResponseEntity.ok(clientDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}



package dev.vdrenkov.service;

import dev.vdrenkov.dto.ClientDto;
import dev.vdrenkov.entity.Client;
import dev.vdrenkov.exception.ClientNotFoundException;
import dev.vdrenkov.mapper.ClientMapper;
import dev.vdrenkov.repository.ClientRepository;
import dev.vdrenkov.request.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Client addClient(ClientRequest clientRequest) {
        Client client = new Client(clientRequest.getName(), clientRequest.getSurname(), clientRequest.getPhoneNumber(),
            clientRequest.getEmail());

        log.info("Trying to add a new client");
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        log.info("Trying to retrieve all clients");
        return clientRepository.findAll();
    }

    public List<ClientDto> getAllClientsDto() {
        return clientMapper.mapClientsToClientsDto(getAllClients());
    }

    public Client getClientById(int id) {
        log.info("Trying to retrieve client with an ID {}", id);
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    public ClientDto getClientDtoById(int id) {
        return clientMapper.mapClientToClientDto(getClientById(id));
    }

    public ClientDto deleteClient(int id) {
        ClientDto clientDto = getClientDtoById(id);

        clientRepository.deleteById(id);

        return clientDto;
    }
}


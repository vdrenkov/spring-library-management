package dev.vdrenkov.biblium.service;

import dev.vdrenkov.biblium.dto.ClientDto;
import dev.vdrenkov.biblium.entity.Client;
import dev.vdrenkov.biblium.exception.ClientNotFoundException;
import dev.vdrenkov.biblium.mapper.ClientMapper;
import dev.vdrenkov.biblium.repository.ClientRepository;
import dev.vdrenkov.biblium.request.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClientService component.
 */
@Service
public class ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    /**
     * Handles ClientService operation.
     *
     * @param clientRepository
     *     Repository dependency used by this component.
     */
    @Autowired
    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Handles addClient operation.
     *
     * @param clientRequest
     *     Request payload with input data.
     * @return Resulting client value.
     */
    public Client addClient(final ClientRequest clientRequest) {
        final Client client = new Client(clientRequest.getName(), clientRequest.getSurname(),
            clientRequest.getPhoneNumber(), clientRequest.getEmail());

        log.info("Trying to add a new client");
        return clientRepository.save(client);
    }

    /**
     * Handles getAllClients operation.
     *
     * @return List of clients.
     */
    public List<Client> getAllClients() {
        log.info("Trying to retrieve all clients");
        return clientRepository.findAll();
    }

    /**
     * Handles getAllClientsDto operation.
     *
     * @return List of client DTOs.
     */
    public List<ClientDto> getAllClientsDto() {
        return ClientMapper.mapClientsToClientsDto(getAllClients());
    }

    /**
     * Handles getClientById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting client value.
     */
    public Client getClientById(final int id) {
        log.info("Trying to retrieve client with an ID {}", id);
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    /**
     * Handles getClientDtoById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting client DTO value.
     */
    public ClientDto getClientDtoById(final int id) {
        return ClientMapper.mapClientToClientDto(getClientById(id));
    }

    /**
     * Handles deleteClient operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Resulting client DTO value.
     */
    public ClientDto deleteClient(final int id) {
        final ClientDto clientDto = getClientDtoById(id);

        clientRepository.deleteById(id);

        return clientDto;
    }
}

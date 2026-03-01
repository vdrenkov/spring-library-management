package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ClientMapper {

  private static final Logger log = LoggerFactory.getLogger(ClientMapper.class);

  public List<ClientDto> mapClientsToClientsDto(final List<Client> clients) {
    final List<ClientDto> clientsDto = new ArrayList<>();

    for (final Client client : clients) {
      clientsDto.add(mapClientToClientDto(client));
    }

    clientsDto.sort(Comparator.comparing(ClientDto::getId));
    log.debug("Clients' list mapped to clients' DTOs list");
    return clientsDto;
  }

  public ClientDto mapClientToClientDto(final Client client) {
    log.debug("Client mapped to client DTO");
    return new ClientDto(client.getId(), client.getName(), client.getSurname(), client.getPhoneNumber(),
                         client.getEmail());
  }
}


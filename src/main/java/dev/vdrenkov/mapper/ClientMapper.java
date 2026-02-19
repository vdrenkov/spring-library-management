package dev.vdrenkov.mapper;

import dev.vdrenkov.dto.ClientDto;
import dev.vdrenkov.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ClientMapper {

  private static final Logger log = LoggerFactory.getLogger(ClientMapper.class);

  public List<ClientDto> mapClientsToClientsDto(List<Client> clients) {
    List<ClientDto> clientsDto = new ArrayList<>();

    for (Client client : clients) {
      clientsDto.add(mapClientToClientDto(client));
    }

    clientsDto.sort(Comparator.comparing(ClientDto::getId));
    log.info("Clients' list mapped to clients' DTOs list");
    return clientsDto;
  }

  public ClientDto mapClientToClientDto(Client client) {
    log.info("Client mapped to client DTO");
    return new ClientDto(client.getId(), client.getName(), client.getSurname(), client.getPhoneNumber(),
                         client.getEmail());
  }
}


package dev.vdrenkov.mapper;

import dev.vdrenkov.dto.ClientDto;
import dev.vdrenkov.util.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientMapperTest {

  private final ClientMapper mapper = new ClientMapper();

  @Test
  public void testMapClientsToClientsDto() {
    List<ClientDto> clientsDto = mapper.mapClientsToClientsDto(ClientFactory.getDefaultClientsList());

    assertNotNull(clientsDto);
  }

  @Test
  public void testMapClientToClientDto() {
    ClientDto clientDto = mapper.mapClientToClientDto(ClientFactory.getDefaultClient());

    assertNotNull(clientDto);
  }
}


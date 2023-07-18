package bg.vdrenkov.mapper;

import bg.vdrenkov.dto.ClientDto;
import bg.vdrenkov.test.util.ClientFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

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
package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.util.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientMapperTest {

    private final ClientMapper mapper = new ClientMapper();

    @Test
    void testMapClientsToClientsDto() {
        List<ClientDto> clientsDto = mapper.mapClientsToClientsDto(ClientFactory.getDefaultClientsList());

        assertNotNull(clientsDto);
    }

    @Test
    void testMapClientToClientDto() {
        ClientDto clientDto = mapper.mapClientToClientDto(ClientFactory.getDefaultClient());

        assertNotNull(clientDto);
    }
}


package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.util.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientMapperTest {

    @Test
    void testMapClientsToClientsDto() {
        final List<ClientDto> clientsDto = ClientMapper.mapClientsToClientsDto(ClientFactory.getDefaultClientsList());

        assertNotNull(clientsDto);
    }

    @Test
    void testMapClientToClientDto() {
        final ClientDto clientDto = ClientMapper.mapClientToClientDto(ClientFactory.getDefaultClient());

        assertNotNull(clientDto);
    }
}

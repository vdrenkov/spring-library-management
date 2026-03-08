package dev.vdrenkov.biblium.util.factory;

import dev.vdrenkov.biblium.dto.ClientDto;
import dev.vdrenkov.biblium.entity.Client;
import dev.vdrenkov.biblium.request.ClientRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.biblium.util.constant.TestConstants.EMAIL;
import static dev.vdrenkov.biblium.util.constant.TestConstants.ID;
import static dev.vdrenkov.biblium.util.constant.TestConstants.NAME;
import static dev.vdrenkov.biblium.util.constant.TestConstants.PHONE_NUMBER;
import static dev.vdrenkov.biblium.util.constant.TestConstants.SURNAME;

/**
 * Test factory for creating default {@link Client}, {@link ClientDto}, and {@link ClientRequest} instances.
 */
public final class ClientFactory {

    /**
     * Utility class constructor.
     */
    private ClientFactory() {
        throw new IllegalStateException("Utility class. Must not be instantiated!");
    }

    /**
     * Creates a default {@link Client} test entity.
     *
     * @return Default client entity.
     */
    public static Client getDefaultClient() {
        return new Client(ID, NAME, SURNAME, PHONE_NUMBER, EMAIL);
    }

    /**
     * Creates a singleton list containing the default client entity.
     *
     * @return Singleton list of clients.
     */
    public static List<Client> getDefaultClientsList() {
        return Collections.singletonList(getDefaultClient());
    }

    /**
     * Creates a default {@link ClientDto}.
     *
     * @return Default client DTO.
     */
    public static ClientDto getDefaultClientDto() {
        return new ClientDto(ID, NAME, SURNAME, PHONE_NUMBER, EMAIL);
    }

    /**
     * Creates a singleton list containing the default client DTO.
     *
     * @return Singleton list of client DTOs.
     */
    public static List<ClientDto> getDefaultClientsDtoList() {
        return Collections.singletonList(getDefaultClientDto());
    }

    /**
     * Creates a default {@link ClientRequest}.
     *
     * @return Default client request.
     */
    public static ClientRequest getDefaultClientRequest() {
        return new ClientRequest(NAME, SURNAME, PHONE_NUMBER, EMAIL);
    }
}


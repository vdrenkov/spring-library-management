package dev.vdrenkov.util;

import dev.vdrenkov.dto.ClientDto;
import dev.vdrenkov.entity.Client;
import dev.vdrenkov.request.ClientRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.util.Constants.EMAIL;
import static dev.vdrenkov.util.Constants.ID;
import static dev.vdrenkov.util.Constants.NAME;
import static dev.vdrenkov.util.Constants.PHONE_NUMBER;
import static dev.vdrenkov.util.Constants.SURNAME;

public final class ClientFactory {

  private ClientFactory() {
    throw new IllegalStateException();
  }

  public static Client getDefaultClient() {
    return new Client(ID, NAME, SURNAME, PHONE_NUMBER, EMAIL);
  }

  public static List<Client> getDefaultClientsList() {
    return Collections.singletonList(getDefaultClient());
  }

  public static ClientDto getDefaultClientDto() {
    return new ClientDto(ID, NAME, SURNAME, PHONE_NUMBER, EMAIL);
  }

  public static List<ClientDto> getDefaultClientsDtoList() {
    return Collections.singletonList(getDefaultClientDto());
  }

  public static ClientRequest getDefaultClientRequest() {
    return new ClientRequest(NAME, SURNAME, PHONE_NUMBER, EMAIL);
  }
}


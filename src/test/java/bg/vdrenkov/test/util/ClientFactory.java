package bg.vdrenkov.test.util;

import bg.vdrenkov.dto.ClientDto;
import bg.vdrenkov.entity.Client;
import bg.vdrenkov.request.ClientRequest;

import java.util.Collections;
import java.util.List;

import static bg.vdrenkov.util.Constants.EMAIL;
import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.PHONE_NUMBER;
import static bg.vdrenkov.util.Constants.SURNAME;

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


package bg.vdrenkov.service;

import bg.vdrenkov.dto.ClientDto;
import bg.vdrenkov.entity.Client;
import bg.vdrenkov.mapper.ClientMapper;
import bg.vdrenkov.repository.ClientRepository;
import bg.vdrenkov.test.util.ClientFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static bg.vdrenkov.util.Constants.ID;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private ClientMapper clientMapper;

  @InjectMocks
  private ClientService clientService;

  @Test
  public void testAddClient() {
    when(clientRepository.save(any())).thenReturn(new Client());

    Client client = clientService.addClient(ClientFactory.getDefaultClientRequest());

    Assert.assertNotNull(client);
  }

  @Test
  public void testGetAllClients() {
    when(clientRepository.findAll()).thenReturn(ClientFactory.getDefaultClientsList());

    List<Client> result = clientService.getAllClients();

    Assert.assertFalse(result.isEmpty());
  }

  @Test
  public void testGetAllClientsDto() {
    when(clientRepository.findAll()).thenReturn(ClientFactory.getDefaultClientsList());
    when(clientMapper.mapClientsToClientsDto(anyList())).thenReturn(ClientFactory.getDefaultClientsDtoList());

    List<ClientDto> result = clientService.getAllClientsDto();

    Assert.assertFalse(result.isEmpty());
  }

  @Test
  public void testGetClientById() {
    when(clientRepository.findById(anyInt())).thenReturn(Optional.of(ClientFactory.getDefaultClient()));

    Client result = clientService.getClientById(ID);

    assertNotNull(result.getName());
  }

  @Test
  public void testGetClientDtoById() {
    when(clientRepository.findById(anyInt())).thenReturn(Optional.of(ClientFactory.getDefaultClient()));
    when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

    ClientDto result = clientService.getClientDtoById(ID);

    assertNotNull(result.getName());
  }
}
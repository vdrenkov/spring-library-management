package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.entity.Client;
import dev.vdrenkov.slm.mapper.ClientMapper;
import dev.vdrenkov.slm.repository.ClientRepository;
import dev.vdrenkov.slm.util.ClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static dev.vdrenkov.slm.util.Constants.ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private ClientMapper clientMapper;

  @InjectMocks
  private ClientService clientService;

  @Test
   void testAddClient() {
   when(clientRepository.save(any())).thenReturn(new Client());

    Client client = clientService.addClient(ClientFactory.getDefaultClientRequest());

    Assertions.assertNotNull(client);
  }

  @Test
   void testGetAllClients() {
    when(clientRepository.findAll()).thenReturn(ClientFactory.getDefaultClientsList());

    List<Client> result = clientService.getAllClients();

    Assertions.assertFalse(result.isEmpty());
  }

  @Test
   void testGetAllClientsDto() {
    when(clientRepository.findAll()).thenReturn(ClientFactory.getDefaultClientsList());
    when(clientMapper.mapClientsToClientsDto(anyList())).thenReturn(ClientFactory.getDefaultClientsDtoList());

    List<ClientDto> result = clientService.getAllClientsDto();

    Assertions.assertFalse(result.isEmpty());
  }

  @Test
   void testGetClientById() {
    when(clientRepository.findById(anyInt())).thenReturn(Optional.of(ClientFactory.getDefaultClient()));

    Client result = clientService.getClientById(ID);

    assertNotNull(result.getName());
  }

  @Test
   void testGetClientDtoById() {
    when(clientRepository.findById(anyInt())).thenReturn(Optional.of(ClientFactory.getDefaultClient()));
    when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

    ClientDto result = clientService.getClientDtoById(ID);

    assertNotNull(result.getName());
  }
}



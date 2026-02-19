package dev.vdrenkov.slm.controller;

import dev.vdrenkov.slm.service.ClientService;
import dev.vdrenkov.slm.util.ClientFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import static dev.vdrenkov.slm.util.Constants.EMAIL;
import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.NAME;
import static dev.vdrenkov.slm.util.Constants.PHONE_NUMBER;
import static dev.vdrenkov.slm.util.Constants.SURNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private static final String URI = "/clients";

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void testAddClient_noExceptions_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ClientFactory.getDefaultClientRequest());
        when(clientService.addClient(any())).thenReturn(ClientFactory.getDefaultClient());

        mockMvc
            .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", URI + "/" + ID));
    }

    @Test
    void testGetAllClients_singleItem_success() throws Exception {
        when(clientService.getAllClientsDto()).thenReturn(ClientFactory.getDefaultClientsDtoList());

        mockMvc
            .perform(get(URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").exists())
            .andExpect(jsonPath("$[0].id").value(ID))
            .andExpect(jsonPath("$[0].name").value(NAME))
            .andExpect(jsonPath("$[0].surname").value(SURNAME))
            .andExpect(jsonPath("$[0].phoneNumber").value(PHONE_NUMBER))
            .andExpect(jsonPath("$[0].email").value(EMAIL));
    }

    @Test
    void testGetAllClients_emptyList_success() throws Exception {
        mockMvc
            .perform(get(URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    void testGetClientById_clientFound_success() throws Exception {
        when(clientService.getClientDtoById(anyInt())).thenReturn(ClientFactory.getDefaultClientDto());

        mockMvc
            .perform(get(URI + "/" + ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID))
            .andExpect(jsonPath("$.name").value(NAME))
            .andExpect(jsonPath("$.surname").value(SURNAME))
            .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(EMAIL));
    }
}



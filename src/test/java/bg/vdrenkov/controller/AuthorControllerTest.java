package bg.vdrenkov.controller;

import bg.vdrenkov.service.AuthorService;
import bg.vdrenkov.test.util.AuthorFactory;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.SURNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

  private final String URI = "/authors";
  private MockMvc mockMvc;

  @Mock
  private AuthorService authorService;

  @InjectMocks
  private AuthorController authorController;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(authorController)
      .build();
  }

  @Test
  public void testAddAuthor_noExceptions_success() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(AuthorFactory.getDefaultAuthorRequest());
    when(authorService.addAuthor(any())).thenReturn(AuthorFactory.getDefaultAuthor());

    mockMvc.perform(post(URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", URI + "/" + ID));
  }

  @Test
  public void testGetAllAuthors_singleAuthor_success() throws Exception {
    when(authorService.getAllAuthorsDto()).thenReturn(AuthorFactory.getDefaultAuthorsDtoList());

    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].surname").value(SURNAME));
  }

  @Test
  public void testGetAllAuthors_emptyList_success() throws Exception {
    when(authorService.getAllAuthorsDto()).thenReturn(Collections.emptyList());

    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void testGetAuthorById_authorFound() throws Exception {
    when(authorService.getAuthorDtoById(ID)).thenReturn(AuthorFactory.getDefaultAuthorDto());

    mockMvc.perform(get(URI + "/" + ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.name").value(NAME))
           .andExpect(jsonPath("$.surname").value(SURNAME));
  }
}



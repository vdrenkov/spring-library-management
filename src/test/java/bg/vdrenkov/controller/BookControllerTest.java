package bg.vdrenkov.controller;

import bg.vdrenkov.service.BookService;
import bg.vdrenkov.test.util.BookFactory;
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

import static bg.vdrenkov.util.Constants.DAY;
import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.MONTH;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.QUANTITY;
import static bg.vdrenkov.util.Constants.SURNAME;
import static bg.vdrenkov.util.Constants.YEAR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  private final String URI = "/books";

  private MockMvc mockMvc;

  @Mock
  private BookService bookService;

  @InjectMocks
  private BookController bookController;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(bookController)
      .build();
  }

  @Test
  public void addBook() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(BookFactory.getDefaultBookRequest());
    when(bookService.addBook(any())).thenReturn(BookFactory.getDefaultBook());

    mockMvc.perform(post(URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", URI + "/" + ID));
  }

  @Test
  public void testGeAllBooks_singleBook_success() throws Exception {
    when(bookService.getAllAvailableBooksDto()).thenReturn(BookFactory.getDefaultBooksDtoList());

    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].publishDate").value("2000-01-01"))
           .andExpect(jsonPath("$[0].authorDto.name").value(NAME))
           .andExpect(jsonPath("$[0].authorDto.surname").value(SURNAME))
           .andExpect(jsonPath("$[0].quantity").value(QUANTITY));
  }

  @Test
  public void testGeAllBooks_emptyList_success() throws Exception {
    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void getAllBooksByAuthor() throws Exception {
    when(bookService.getAllBooksDtoByAuthor(anyInt())).thenReturn(BookFactory.getDefaultBooksDtoList());

    mockMvc.perform(get("/authors/"+ID+URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].publishDate").value("2000-01-01"))
           .andExpect(jsonPath("$[0].authorDto.name").value(NAME))
           .andExpect(jsonPath("$[0].authorDto.surname").value(SURNAME))
           .andExpect(jsonPath("$[0].quantity").value(QUANTITY));
  }

  @Test
  public void getBookById() throws Exception {
    when(bookService.getBookDtoById(anyInt())).thenReturn(BookFactory.getDefaultBookDto());

    mockMvc.perform(get(URI + "/" + ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.name").value(NAME))
           .andExpect(jsonPath("$.publishDate").value("2000-01-01"))
           .andExpect(jsonPath("$.authorDto.name").value(NAME))
           .andExpect(jsonPath("$.authorDto.surname").value(SURNAME))
           .andExpect(jsonPath("$.quantity").value(QUANTITY));
  }
}




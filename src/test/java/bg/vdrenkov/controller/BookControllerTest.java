package bg.vdrenkov.controller;

import bg.vdrenkov.service.BookService;
import bg.vdrenkov.test.util.BookFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

  private final String URI = "/books";

  private MockMvc mockMvc;

  @Mock
  private BookService bookService;

  @InjectMocks
  private BookController bookController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(bookController)
      .build();
  }

  @Test
  public void addBook() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
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
           .andExpect(jsonPath("$[0].publishDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].publishDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].publishDate[2]").value(DAY))
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
           .andExpect(jsonPath("$[0].publishDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].publishDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].publishDate[2]").value(DAY))
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
           .andExpect(jsonPath("$.publishDate[0]").value(YEAR))
           .andExpect(jsonPath("$.publishDate[1]").value(MONTH))
           .andExpect(jsonPath("$.publishDate[2]").value(DAY))
           .andExpect(jsonPath("$.authorDto.name").value(NAME))
           .andExpect(jsonPath("$.authorDto.surname").value(SURNAME))
           .andExpect(jsonPath("$.quantity").value(QUANTITY));
  }
}
package bg.vdrenkov.handler;

import bg.vdrenkov.controller.OrderController;
import bg.vdrenkov.exception.AuthorNotFoundException;
import bg.vdrenkov.exception.BookNotFoundException;
import bg.vdrenkov.exception.ClientNotFoundException;
import bg.vdrenkov.exception.OrderNotFoundException;
import bg.vdrenkov.request.OrderRequest;
import bg.vdrenkov.service.OrderService;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.UnexpectedTypeException;
import java.time.format.DateTimeParseException;

import static bg.vdrenkov.util.Constants.CHOICE;
import static bg.vdrenkov.util.Constants.CHOICE_STRING;
import static bg.vdrenkov.util.Constants.ID;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

  private final String URI = "/orders";
  private final String ROOT = "$";

  private MockMvc mockMvc;

  @Mock
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(orderController)
      .setControllerAdvice(new GlobalExceptionHandler())
      .build();
  }

  @Test
  public void handleMethodArgumentNotValidException_onEndpointGetAllOrders_badRequest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String json = objectMapper.writeValueAsString(new OrderRequest(0, BookFactory.getDefaultBooksIdsList()));

    mockMvc.perform(post(URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("The client id must be a positive digit")));
  }

  @Test
  public void testHandleNullPointerException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(NullPointerException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Missing or incorrect data entered")));
  }

  @Test
  public void testHandleUnexpectedTypeException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(UnexpectedTypeException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Wrong data entered")));
  }

  @Test
  public void testHandleHttpMessageNotReadableException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(HttpMessageNotReadableException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Incorrect data entered")));
  }

  @Test
  public void testHandleHttpRequestMethodNotSupportedException_onEndpointGetAllOrders_badRequest() throws Exception {
    mockMvc.perform(put(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("HTTP method not supported")));
  }

  @Test
  public void testHandleIllegalArgumentException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(IllegalArgumentException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Illegal argument provided")));
  }

  @Test
  public void testHandleMissingServletRequestParameterException_onEndpointGetAllOrders_badRequest() throws Exception {
    mockMvc.perform(put(URI + "/" + ID))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Invalid parameter provided")));
  }

  @Test
  public void testHandleDateTimeParseException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDtoByDate(CHOICE, "mistake")).thenThrow(DateTimeParseException.class);

    mockMvc.perform(get(URI)
                      .queryParam("choice", CHOICE_STRING)
                      .queryParam("date", "mistake"))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Invalid date provided")));
  }

  @Test
  public void testHandleMethodArgumentTypeMismatchException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(MethodArgumentTypeMismatchException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("Invalid URL provided")));
  }

  @Test
  public void testHandleDataIntegrityViolationException_onEndpointGetAllOrders_badRequest() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(DataIntegrityViolationException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath(ROOT, is("You are violating database rule/s")));
  }

  @Test
  public void testHandleAuthorNotFoundException_onEndpointGetAllOrders_notFound() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(AuthorNotFoundException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath(ROOT, is("No such author was found in the database")));
  }

  @Test
  public void testHandleBookNotFoundException_onEndpointGetAllOrders_notFound() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(BookNotFoundException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath(ROOT, is("No such book was found in the database")));
  }

  @Test
  public void testHandleClientNotFoundException_onEndpointGetAllOrders_notFound() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(ClientNotFoundException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath(ROOT, is("No such client was found in the database")));
  }

  @Test
  public void testHandleOrderNotFoundException_onEndpointGetAllOrders_notFound() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(OrderNotFoundException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath(ROOT, is("No such order was found in the database")));
  }

  @Test
  public void testHandleException_onEndpointGetAllOrders_internalServerError() throws Exception {
    when(orderService.getAllOrdersDto()).thenThrow(IllegalStateException.class);

    mockMvc.perform(get(URI))
           .andExpect(status().isInternalServerError())
           .andExpect(jsonPath(ROOT, is("Something went wrong")));
  }
}
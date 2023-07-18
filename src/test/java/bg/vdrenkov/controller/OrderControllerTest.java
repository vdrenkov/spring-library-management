package bg.vdrenkov.controller;

import bg.vdrenkov.service.OrderService;
import bg.vdrenkov.test.util.OrderFactory;
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

import static bg.vdrenkov.util.Constants.CHOICE_STRING;
import static bg.vdrenkov.util.Constants.DATE_STRING;
import static bg.vdrenkov.util.Constants.DAY;
import static bg.vdrenkov.util.Constants.EMAIL;
import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.MONTH;
import static bg.vdrenkov.util.Constants.NAME;
import static bg.vdrenkov.util.Constants.ONE;
import static bg.vdrenkov.util.Constants.PERIOD_STRING;
import static bg.vdrenkov.util.Constants.PHONE_NUMBER;
import static bg.vdrenkov.util.Constants.SURNAME;
import static bg.vdrenkov.util.Constants.YEAR;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

  private final String URI = "/orders";
  private MockMvc mockMvc;

  @Mock
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(orderController)
      .build();
  }

  @Test
  public void testAddOrder_noExceptions_success() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String json = objectMapper.writeValueAsString(OrderFactory.getDefaultOrderRequest());
    when(orderService.addOrder(any())).thenReturn(OrderFactory.getDefaultOrder());

    mockMvc.perform(post(URI)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", URI + "/" + ID));
  }

  @Test
  public void testGetAllOrders_singleOrder_success() throws Exception {
    when(orderService.getAllOrdersDto()).thenReturn(OrderFactory.getDefaultOrdersDtoList());

    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].clientDto.name").value(NAME))
           .andExpect(jsonPath("$[0].clientDto.surname").value(SURNAME))
           .andExpect(jsonPath("$[0].clientDto.phoneNumber").value(PHONE_NUMBER))
           .andExpect(jsonPath("$[0].clientDto.email").value(EMAIL))
           .andExpect(jsonPath("$[0].booksNames", hasSize(ONE)))
           .andExpect(jsonPath("$[0].issueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].issueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].issueDate[2]").value(DAY))
           .andExpect(jsonPath("$[0].dueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].dueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].dueDate[2]").value(DAY));
  }

  @Test
  public void testGetAllOrders_emptyList_success() throws Exception {
    mockMvc.perform(get(URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void testGetALlOrdersByClient_singleOrder_success() throws Exception {
    when(orderService.getAllOrdersDtoByClient(anyInt())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

    mockMvc.perform(get("/clients/" + ID + URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].clientDto.name").value(NAME))
           .andExpect(jsonPath("$[0].clientDto.surname").value(SURNAME))
           .andExpect(jsonPath("$[0].clientDto.phoneNumber").value(PHONE_NUMBER))
           .andExpect(jsonPath("$[0].clientDto.email").value(EMAIL))
           .andExpect(jsonPath("$[0].booksNames", hasSize(ONE)))
           .andExpect(jsonPath("$[0].issueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].issueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].issueDate[2]").value(DAY))
           .andExpect(jsonPath("$[0].dueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].dueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].dueDate[2]").value(DAY));
  }

  @Test
  public void testGetAllOrdersByClient_emptyList_success() throws Exception {
    mockMvc.perform(get("/clients/" + ID + URI))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void testGetALlOrdersByDate_singleOrder_success() throws Exception {
    when(orderService.getAllOrdersDtoByDate(anyInt(), anyString())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

    mockMvc.perform(get(URI)
                      .queryParam("choice", CHOICE_STRING)
                      .queryParam("date", DATE_STRING))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].clientDto.name").value(NAME))
           .andExpect(jsonPath("$[0].clientDto.surname").value(SURNAME))
           .andExpect(jsonPath("$[0].clientDto.phoneNumber").value(PHONE_NUMBER))
           .andExpect(jsonPath("$[0].clientDto.email").value(EMAIL))
           .andExpect(jsonPath("$[0].booksNames", hasSize(ONE)))
           .andExpect(jsonPath("$[0].issueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].issueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].issueDate[2]").value(DAY))
           .andExpect(jsonPath("$[0].dueDate[0]").value(YEAR))
           .andExpect(jsonPath("$[0].dueDate[1]").value(MONTH))
           .andExpect(jsonPath("$[0].dueDate[2]").value(DAY));
  }

  @Test
  public void testGetAllOrdersByDate_emptyList_success() throws Exception {
    mockMvc.perform(get(URI)
                      .queryParam("choice", CHOICE_STRING)
                      .queryParam("dateValue", DATE_STRING))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").exists())
           .andExpect(jsonPath("$[0]").doesNotExist());
  }

  @Test
  public void testGetOrderById_noExceptions_success() throws Exception {
    when(orderService.getOrderDtoById(anyInt())).thenReturn(OrderFactory.getDefaultOrderDto());

    mockMvc.perform(get(URI + "/" + ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.clientDto.name").value(NAME))
           .andExpect(jsonPath("$.clientDto.surname").value(SURNAME))
           .andExpect(jsonPath("$.clientDto.phoneNumber").value(PHONE_NUMBER))
           .andExpect(jsonPath("$.clientDto.email").value(EMAIL))
           .andExpect(jsonPath("$.booksNames", hasSize(ONE)))
           .andExpect(jsonPath("$.issueDate[0]").value(YEAR))
           .andExpect(jsonPath("$.issueDate[1]").value(MONTH))
           .andExpect(jsonPath("$.issueDate[2]").value(DAY))
           .andExpect(jsonPath("$.dueDate[0]").value(YEAR))
           .andExpect(jsonPath("$.dueDate[1]").value(MONTH))
           .andExpect(jsonPath("$.dueDate[2]").value(DAY));
  }

  @Test
  public void testExtendOrderDueByDate_requestedResponse_success() throws Exception {
    when(orderService.extendOrderDueByDate(anyInt(), anyInt(), anyInt())).thenReturn(OrderFactory.getDefaultOrderDto());

    mockMvc.perform(put(URI + "/" + ID)
                      .queryParam("choice", CHOICE_STRING)
                      .queryParam("period", PERIOD_STRING)
                      .queryParam("returnOld", "true"))
           .andExpect(status().isOk());
  }

  @Test
  public void testExtendOrderDueByDate_noResponse_success() throws Exception {
    when(orderService.extendOrderDueByDate(anyInt(), anyInt(), anyInt())).thenReturn(OrderFactory.getDefaultOrderDto());

    mockMvc.perform(put(URI + "/" + ID)
                      .queryParam("choice", CHOICE_STRING)
                      .queryParam("period", PERIOD_STRING))
           .andExpect(status().isNoContent());
  }
}
package bg.vdrenkov.service;

import bg.vdrenkov.dto.OrderDto;
import bg.vdrenkov.entity.Order;
import bg.vdrenkov.mapper.LocalDateMapper;
import bg.vdrenkov.mapper.OrderMapper;
import bg.vdrenkov.repository.OrderRepository;
import bg.vdrenkov.test.util.BookFactory;
import bg.vdrenkov.test.util.ClientFactory;
import bg.vdrenkov.test.util.OrderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bg.vdrenkov.util.Constants.CHOICE;
import static bg.vdrenkov.util.Constants.DATE_STRING;
import static bg.vdrenkov.util.Constants.DAY;
import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.LOCAL_DATE;
import static bg.vdrenkov.util.Constants.MONTH;
import static bg.vdrenkov.util.Constants.ONE;
import static bg.vdrenkov.util.Constants.PERIOD;
import static bg.vdrenkov.util.Constants.YEAR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @Mock
  private BookService bookService;

  @Mock
  private ClientService clientService;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private LocalDateMapper localDateMapper;

  @Mock
  private OrderMapper orderMapper;

  @InjectMocks
  private OrderService orderService;

  @Test
  public void testAddOrder_success() {
    when(clientService.getClientById(anyInt())).thenReturn(ClientFactory.getDefaultClient());
    when(bookService.isBookAvailable(anyInt())).thenReturn(true);
    when(bookService.updateBookQuantity(anyInt())).thenReturn(BookFactory.getDefaultBook());
    when(orderRepository.save(any())).thenReturn(new Order());

    Order order = orderService.addOrder(OrderFactory.getDefaultOrderRequest());

    assertNotNull(order);
  }

  @Test
  public void testAddOrder_clientNull() {
    when(clientService.getClientById(ID)).thenReturn(null);

    Order order = orderService.addOrder(OrderFactory.getDefaultOrderRequest());

    assertNull(order);
  }

  @Test
  public void testAddOrder_noBooksIds() {
    when(clientService.getClientById(anyInt())).thenReturn(ClientFactory.getDefaultClient());

    Order order = orderService.addOrder(OrderFactory.getDefaultOrderRequest());

    assertNull(order);
  }

  @Test
  public void testUpdateBookQuantity_success() {
    when(bookService.isBookAvailable(anyInt())).thenReturn(true);
    when(bookService.updateBookQuantity(anyInt())).thenReturn(BookFactory.getDefaultBook());

    boolean result = orderService.updateBookQuantity(BookFactory.getDefaultBooksIdsList());

    assertTrue(result);
  }

  @Test
  public void testUpdateBookQuantity_noBooksIds() {
    boolean result = orderService.updateBookQuantity(Collections.emptyList());

    assertFalse(result);
  }

  @Test
  public void testUpdateBookQuantity_bookNotAvailable() {
    when(bookService.isBookAvailable(anyInt())).thenReturn(false);

    boolean result = orderService.updateBookQuantity(BookFactory.getDefaultBooksIdsList());

    assertFalse(result);
  }

  @Test
  public void testUpdateBookQuantity_bookNotUpdated() {
    when(bookService.isBookAvailable(anyInt())).thenReturn(true);
    when(bookService.updateBookQuantity(anyInt())).thenReturn(null);

    boolean result = orderService.updateBookQuantity(BookFactory.getDefaultBooksIdsList());

    assertFalse(result);
  }

  @Test
  public void testGetAllOrders() {
    when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());

    List<Order> testOrders = orderService.getAllOrders();

    assertNotNull(testOrders);
  }

  @Test
  public void testGetAllOrdersDto() {
    when(orderMapper.mapOrdersToOrdersDto(anyList())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

    List<OrderDto> result = orderService.getAllOrdersDto();

    assertNotNull(result);
  }

  @Test
  public void testGetAllOrdersByClient() {
    when(orderService.getAllOrders()).thenReturn(OrderFactory.getDefaultOrdersList());

    List<Order> testOrders = orderService.getAllOrdersByClient(ID);

    assertNotNull(testOrders);
  }

  @Test
  public void testGetAllOrdersDtoByClient() {
    when(orderMapper.mapOrdersToOrdersDto(anyList())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

    List<OrderDto> result = orderService.getAllOrdersDtoByClient(ID);

    assertNotNull(result);
  }

  @Test
  public void testGetAllOrdersByDate() {
    when(orderService.getAllOrders()).thenReturn(OrderFactory.getDefaultOrdersList());

    List<Order> testOrders = orderService.getAllOrdersByDate(ONE, LocalDate.of(YEAR, MONTH, DAY));

    assertNotNull(testOrders);
  }

  @Test
  public void testGetAllOrdersDtoByDate() {
    when(localDateMapper.mapStringToDate(DATE_STRING)).thenReturn(LOCAL_DATE);

    List<OrderDto> result = orderService.getAllOrdersDtoByDate(CHOICE, DATE_STRING);

    assertNotNull(result);
  }

  @Test
  public void testGetOrderByDateFilter_case1() {
    LocalDate date = LocalDate.of(2000, 1, 1);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 1, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_case2() {
    LocalDate date = LocalDate.of(2000, 1, 2);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 2, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_case3() {
    LocalDate date = LocalDate.of(1999, 1, 1);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 3, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_case4() {
    LocalDate date = LocalDate.of(2000, 1, 1);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 4, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_case5() {
    LocalDate date = LocalDate.of(2000, 1, 2);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 5, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_case6() {
    LocalDate date = LocalDate.of(1999, 1, 1);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 6, date);

    assertNotNull(testOrder);
  }

  @Test
  public void testGetOrderByDateFilter_caseNull() {
    LocalDate date = LocalDate.of(1999, 1, 1);

    Order testOrder = orderService.getOrderByDateFilter(OrderFactory.getDefaultOrder(), 1, date);

    assertNull(testOrder);
  }

  @Test
  public void testGetOrderById() {
    when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));

    Order order = orderService.getOrderById(ID);

    assertNotNull(order);
  }

  @Test
  public void testGetOrderDtoById() {
    when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));
    when(orderMapper.mapOrderToOrderDto(any())).thenReturn(OrderFactory.getDefaultOrderDto());

    OrderDto orderDto = orderService.getOrderDtoById(ID);

    assertNotNull(orderDto);
  }

  @Test
  public void testExtendOrderDueByDate_success() {
    when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));
    when(orderRepository.save(any())).thenReturn(OrderFactory.getDefaultOrder());
    when(orderMapper.mapOrderToOrderDto(any())).thenReturn(OrderFactory.getDefaultOrderDto());

    OrderDto orderDto1 = orderService.extendOrderDueByDate(ID, 1, PERIOD);
    OrderDto orderDto2 = orderService.extendOrderDueByDate(ID, 2, PERIOD);
    OrderDto orderDto3 = orderService.extendOrderDueByDate(ID, 3, PERIOD);
    boolean result = orderDto1 != null && orderDto2 != null && orderDto3 != null;

    assertTrue(result);
  }
}
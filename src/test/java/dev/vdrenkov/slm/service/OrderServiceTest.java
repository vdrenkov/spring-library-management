package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Order;
import dev.vdrenkov.slm.mapper.LocalDateMapper;
import dev.vdrenkov.slm.mapper.OrderMapper;
import dev.vdrenkov.slm.repository.OrderRepository;
import dev.vdrenkov.slm.util.BookFactory;
import dev.vdrenkov.slm.util.ClientFactory;
import dev.vdrenkov.slm.util.OrderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dev.vdrenkov.slm.util.Constants.CHOICE;
import static dev.vdrenkov.slm.util.Constants.DATE_STRING;
import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.LOCAL_DATE;
import static dev.vdrenkov.slm.util.Constants.PERIOD;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

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
    void testAddOrder_success() {
        when(clientService.getClientById(anyInt())).thenReturn(ClientFactory.getDefaultClient());
        when(bookService.getBookById(anyInt())).thenReturn(BookFactory.getDefaultBook());
        when(bookService.decreaseBookQuantity(anyInt())).thenReturn(BookFactory.getDefaultBook());
        when(orderRepository.save(any())).thenReturn(new Order());

        Order order = orderService.addOrder(OrderFactory.getDefaultOrderRequest());

        assertNotNull(order);
    }

    @Test
    void testAddOrder_noBooksIds_throws() {
        assertThrows(IllegalArgumentException.class, () ->
            orderService.addOrder(new dev.vdrenkov.slm.request.OrderRequest(ID, Collections.emptyList())));
    }

    @Test
    void testAddOrder_bookOutOfStock_throws() {
        when(clientService.getClientById(anyInt())).thenReturn(ClientFactory.getDefaultClient());
        when(bookService.getBookById(anyInt())).thenReturn(BookFactory.getDefaultBook());
        when(bookService.decreaseBookQuantity(anyInt())).thenThrow(new IllegalStateException("Book is out of stock"));

        assertThrows(IllegalStateException.class, () -> orderService.addOrder(OrderFactory.getDefaultOrderRequest()));
    }

    @Test
    void testAddOrder_duplicateBookIds_throws() {
        assertThrows(IllegalArgumentException.class, () ->
            orderService.addOrder(new dev.vdrenkov.slm.request.OrderRequest(ID, List.of(ID, ID))));
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());

        List<Order> testOrders = orderService.getAllOrders();

        assertNotNull(testOrders);
    }

    @Test
    void testGetAllOrdersDto() {
        when(orderMapper.mapOrdersToOrdersDto(anyList())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

        List<OrderDto> result = orderService.getAllOrdersDto();

        assertNotNull(result);
    }

    @Test
    void testGetAllOrdersByClient() {
        when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());

        List<Order> testOrders = orderService.getAllOrdersByClient(ID);

        assertNotNull(testOrders);
    }

    @Test
    void testGetAllOrdersDtoByClient() {
        when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());
        when(orderMapper.mapOrdersToOrdersDto(anyList())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

        List<OrderDto> result = orderService.getAllOrdersDtoByClient(ID);

        assertNotNull(result);
    }

    @Test
    void testGetAllOrdersByDate() {
        when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());

        List<Order> testOrders = orderService.getAllOrdersByDate(1, LOCAL_DATE);

        assertNotNull(testOrders);
    }

    @Test
    void testGetAllOrdersByDate_invalidChoice_throws() {
        assertThrows(IllegalArgumentException.class, () -> orderService.getAllOrdersByDate(99, LOCAL_DATE));
    }

    @Test
    void testGetAllOrdersDtoByDate() {
        when(localDateMapper.mapStringToDate(anyString())).thenReturn(LOCAL_DATE);
        when(orderRepository.findAll()).thenReturn(OrderFactory.getDefaultOrdersList());
        when(orderMapper.mapOrdersToOrdersDto(anyList())).thenReturn(OrderFactory.getDefaultOrdersDtoList());

        List<OrderDto> result = orderService.getAllOrdersDtoByDate(CHOICE, DATE_STRING);

        assertNotNull(result);
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));

        Order order = orderService.getOrderById(ID);

        assertNotNull(order);
    }

    @Test
    void testGetOrderDtoById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));
        when(orderMapper.mapOrderToOrderDto(any())).thenReturn(OrderFactory.getDefaultOrderDto());

        OrderDto orderDto = orderService.getOrderDtoById(ID);

        assertNotNull(orderDto);
    }

    @Test
    void testExtendOrderDueByDate_success() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));
        when(orderRepository.save(any())).thenReturn(OrderFactory.getDefaultOrder());
        when(orderMapper.mapOrderToOrderDto(any())).thenReturn(OrderFactory.getDefaultOrderDto());

        OrderDto orderDto1 = orderService.extendOrderDueByDate(ID, 1, PERIOD);
        OrderDto orderDto2 = orderService.extendOrderDueByDate(ID, 2, PERIOD);
        OrderDto orderDto3 = orderService.extendOrderDueByDate(ID, 3, PERIOD);
        boolean result = orderDto1 != null && orderDto2 != null && orderDto3 != null;

        assertTrue(result);
    }

    @Test
    void testExtendOrderDueByDate_invalidChoice_throws() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(OrderFactory.getDefaultOrder()));

        assertThrows(IllegalArgumentException.class, () -> orderService.extendOrderDueByDate(ID, 99, PERIOD));
    }
}

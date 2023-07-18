package bg.vdrenkov.service;

import bg.vdrenkov.dto.OrderDto;
import bg.vdrenkov.entity.Book;
import bg.vdrenkov.entity.Client;
import bg.vdrenkov.entity.Order;
import bg.vdrenkov.exception.OrderNotFoundException;
import bg.vdrenkov.mapper.LocalDateMapper;
import bg.vdrenkov.mapper.OrderMapper;
import bg.vdrenkov.repository.OrderRepository;
import bg.vdrenkov.request.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static bg.vdrenkov.util.Constants.NOW;
import static bg.vdrenkov.util.Constants.ZERO;

@Service
public class OrderService {

  private static final Logger log = LoggerFactory.getLogger(OrderService.class);
  private final BookService bookService;
  private final ClientService clientService;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final LocalDateMapper localDateMapper;

  @Autowired
  public OrderService(
    BookService bookService, ClientService clientService, OrderRepository orderRepository, OrderMapper orderMapper,
    LocalDateMapper localDateMapper) {
    this.bookService = bookService;
    this.clientService = clientService;
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.localDateMapper = localDateMapper;
  }

  public Order addOrder(OrderRequest orderRequest) {
    Client client = clientService.getClientById(orderRequest.getClientId());
    List<Book> books = new ArrayList<>();

    for (int bookId : orderRequest.getBooksIds()) {
      books.add(bookService.getBookById(bookId));
    }

    if (!updateBookQuantity(orderRequest.getBooksIds())) {
      return null;
    }

    log.info("Trying to add a new order");
    return orderRepository.save(new Order(client, books, NOW, NOW.plusMonths(1)));
  }

  public boolean updateBookQuantity(List<Integer> booksIds) {
    if (booksIds.size() == ZERO) {
      return false;
    }

    for (int bookId : booksIds) {
      if (!bookService.isBookAvailable(bookId)) {
        return false;
      }
    }

    for (int bookId : booksIds) {
      if (Objects.isNull(bookService.updateBookQuantity(bookId))) {
        return false;
      }
    }

    return true;
  }

  public List<Order> getAllOrders() {
    log.info("Trying to retrieve all orders");
    return orderRepository.findAll();
  }

  public List<OrderDto> getAllOrdersDto() {
    return orderMapper.mapOrdersToOrdersDto(getAllOrders());
  }

  public List<Order> getAllOrdersByClient(int clientId) {
    List<Order> ordersByClient = new ArrayList<>();
    List<Order> allOrders = getAllOrders();

    for (Order order : allOrders) {
      if (order.getClient().getId() == clientId) {
        ordersByClient.add(order);
      }
    }
    return ordersByClient;
  }

  public List<OrderDto> getAllOrdersDtoByClient(int clientId) {
    return orderMapper.mapOrdersToOrdersDto(getAllOrdersByClient(clientId));
  }

  public List<Order> getAllOrdersByDate(int choice, LocalDate date) {
    List<Order> ordersByDate = new ArrayList<>();
    List<Order> allOrders = getAllOrders();

    for (Order order : allOrders) {
      Order desiredOrder = getOrderByDateFilter(order, choice, date);
      if (desiredOrder != null) {
        ordersByDate.add(desiredOrder);
      }
    }
    return ordersByDate;
  }

  public List<OrderDto> getAllOrdersDtoByDate(int choice, String date) {
    return orderMapper.mapOrdersToOrdersDto(getAllOrdersByDate(choice, localDateMapper.mapStringToDate(date)));
  }

  public Order getOrderByDateFilter(Order order, int choice, LocalDate date) {
    switch (choice) {
      case 1:
        if (order.getIssueDate().isEqual(date)) {
          return order;
        }
        break;
      case 2:
        if (order.getIssueDate().isBefore(date)) {
          return order;
        }
        break;
      case 3:
        if (order.getIssueDate().isAfter(date)) {
          return order;
        }
        break;
      case 4:
        if (order.getDueDate().isEqual(date)) {
          return order;
        }
        break;
      case 5:
        if (order.getDueDate().isBefore(date)) {
          return order;
        }
        break;
      case 6:
        if (order.getDueDate().isAfter(date)) {
          return order;
        }
        break;
    }
    return null;
  }

  public Order getOrderById(int id) {
    log.info(String.format("Trying to find order with id %d", id));
    return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
  }

  public OrderDto getOrderDtoById(int id) {
    return orderMapper.mapOrderToOrderDto(getOrderById(id));
  }

  public OrderDto extendOrderDueByDate(int orderId, int choice, int period) {
    Order order = getOrderById(orderId);

    LocalDate dueDate = order.getDueDate();

    switch (choice) {
      case 1:
        order.setDueDate(dueDate.plusDays(period));
        break;
      case 2:
        order.setDueDate(dueDate.plusWeeks(period));
        break;
      case 3:
        order.setDueDate(dueDate.plusMonths(period));
        break;
    }
    orderRepository.save(order);
    order.setDueDate(dueDate);
    log.info("Order due date successfully updated");
    return orderMapper.mapOrderToOrderDto(order);
  }
}
package dev.vdrenkov.slm.service;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.entity.Client;
import dev.vdrenkov.slm.entity.Order;
import dev.vdrenkov.slm.exception.OrderNotFoundException;
import dev.vdrenkov.slm.mapper.LocalDateMapper;
import dev.vdrenkov.slm.mapper.OrderMapper;
import dev.vdrenkov.slm.repository.OrderRepository;
import dev.vdrenkov.slm.request.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final BookService bookService;
    private final ClientService clientService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final LocalDateMapper localDateMapper;

    @Autowired
    public OrderService(BookService bookService, ClientService clientService, OrderRepository orderRepository,
        OrderMapper orderMapper, LocalDateMapper localDateMapper) {
        this.bookService = bookService;
        this.clientService = clientService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.localDateMapper = localDateMapper;
    }

    @Transactional
    public Order addOrder(OrderRequest orderRequest) {
        if (orderRequest.getBooksIds().isEmpty()) {
            throw new IllegalArgumentException("At least one book ID is required to create an order");
        }
        validateNoDuplicateBookIds(orderRequest.getBooksIds());

        Client client = clientService.getClientById(orderRequest.getClientId());
        List<Book> books = new ArrayList<>();

        for (int bookId : orderRequest.getBooksIds()) {
            books.add(bookService.getBookById(bookId));
        }

        decreaseBooksQuantities(orderRequest.getBooksIds());

        LocalDate issueDate = LocalDate.now();

        log.info("Trying to add a new order");
        return orderRepository.save(new Order(client, books, issueDate, issueDate.plusMonths(1)));
    }

    private void validateNoDuplicateBookIds(List<Integer> booksIds) {
        if (new HashSet<>(booksIds).size() != booksIds.size()) {
            throw new IllegalArgumentException("Duplicate book IDs are not allowed in a single order");
        }
    }

    private void decreaseBooksQuantities(List<Integer> booksIds) {
        for (int bookId : booksIds) {
            bookService.decreaseBookQuantity(bookId);
        }
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
        validateOrderDateChoice(choice);

        List<Order> ordersByDate = new ArrayList<>();
        List<Order> allOrders = getAllOrders();

        for (Order order : allOrders) {
            if (matchesOrderDateFilter(order, choice, date)) {
                ordersByDate.add(order);
            }
        }
        return ordersByDate;
    }

    public List<OrderDto> getAllOrdersDtoByDate(int choice, String date) {
        return orderMapper.mapOrdersToOrdersDto(getAllOrdersByDate(choice, localDateMapper.mapStringToDate(date)));
    }

    private boolean matchesOrderDateFilter(Order order, int choice, LocalDate date) {
        switch (choice) {
            case 1:
                return order.getIssueDate().isEqual(date);
            case 2:
                return order.getIssueDate().isBefore(date);
            case 3:
                return order.getIssueDate().isAfter(date);
            case 4:
                return order.getDueDate().isEqual(date);
            case 5:
                return order.getDueDate().isBefore(date);
            case 6:
                return order.getDueDate().isAfter(date);
            default:
                return false;
        }
    }

    private void validateOrderDateChoice(int choice) {
        if (choice < 1 || choice > 6) {
            throw new IllegalArgumentException("Choice must be between 1 and 6");
        }
    }

    public Order getOrderById(int id) {
        log.info("Trying to find order with an ID {}", id);
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public OrderDto getOrderDtoById(int id) {
        return orderMapper.mapOrderToOrderDto(getOrderById(id));
    }

    @Transactional
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
            default:
                throw new IllegalArgumentException("Choice must be between 1 and 3");
        }
        orderRepository.save(order);
        order.setDueDate(dueDate);
        log.info("Order due date successfully updated");
        return orderMapper.mapOrderToOrderDto(order);
    }
}

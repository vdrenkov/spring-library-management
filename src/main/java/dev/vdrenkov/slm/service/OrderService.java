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
    public OrderService(final BookService bookService, final ClientService clientService, final OrderRepository orderRepository,
        final OrderMapper orderMapper, final LocalDateMapper localDateMapper) {
        this.bookService = bookService;
        this.clientService = clientService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.localDateMapper = localDateMapper;
    }

    @Transactional
    public Order addOrder(final OrderRequest orderRequest) {
        if (orderRequest.getBooksIds().isEmpty()) {
            throw new IllegalArgumentException("At least one book ID is required to create an order");
        }
        validateNoDuplicateBookIds(orderRequest.getBooksIds());

        final Client client = clientService.getClientById(orderRequest.getClientId());
        final List<Book> books = new ArrayList<>();

        for (final int bookId : orderRequest.getBooksIds()) {
            books.add(bookService.getBookById(bookId));
        }

        decreaseBooksQuantities(orderRequest.getBooksIds());

        final LocalDate issueDate = LocalDate.now();

        log.info("Trying to add a new order");
        return orderRepository.save(new Order(client, books, issueDate, issueDate.plusMonths(1)));
    }

    private void validateNoDuplicateBookIds(final List<Integer> booksIds) {
        if (new HashSet<>(booksIds).size() != booksIds.size()) {
            throw new IllegalArgumentException("Duplicate book IDs are not allowed in a single order");
        }
    }

    private void decreaseBooksQuantities(final List<Integer> booksIds) {
        for (final int bookId : booksIds) {
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

    public List<Order> getAllOrdersByClient(final int clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<OrderDto> getAllOrdersDtoByClient(final int clientId) {
        return orderMapper.mapOrdersToOrdersDto(getAllOrdersByClient(clientId));
    }

    public List<Order> getAllOrdersByDate(final int choice, final LocalDate date) {
        validateOrderDateChoice(choice);
        return switch (choice) {
            case 1 -> orderRepository.findByIssueDate(date);
            case 2 -> orderRepository.findByIssueDateBefore(date);
            case 3 -> orderRepository.findByIssueDateAfter(date);
            case 4 -> orderRepository.findByDueDate(date);
            case 5 -> orderRepository.findByDueDateBefore(date);
            case 6 -> orderRepository.findByDueDateAfter(date);
            default -> List.of();
        };
    }

    public List<OrderDto> getAllOrdersDtoByDate(final int choice, final String date) {
        return orderMapper.mapOrdersToOrdersDto(getAllOrdersByDate(choice, localDateMapper.mapStringToDate(date)));
    }

    private void validateOrderDateChoice(final int choice) {
        if (choice < 1 || choice > 6) {
            throw new IllegalArgumentException("Choice must be between 1 and 6");
        }
    }

    public Order getOrderById(final int id) {
        log.info("Trying to find order with an ID {}", id);
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public OrderDto getOrderDtoById(final int id) {
        return orderMapper.mapOrderToOrderDto(getOrderById(id));
    }

    @Transactional
    public OrderDto extendOrderDueByDate(final int orderId, final int choice, final int period) {
        final Order order = getOrderById(orderId);
        final OrderDto oldOrder = orderMapper.mapOrderToOrderDto(order);

        final LocalDate dueDate = order.getDueDate();

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
        log.info("Order due date successfully updated");
        return oldOrder;
    }
}

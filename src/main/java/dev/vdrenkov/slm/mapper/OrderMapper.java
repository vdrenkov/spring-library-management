package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class OrderMapper {

  private static final Logger log = LoggerFactory.getLogger(OrderMapper.class);

  private final ClientMapper clientMapper;

  @Autowired
  public OrderMapper(final ClientMapper clientMapper) {
    this.clientMapper = clientMapper;
  }

  public List<OrderDto> mapOrdersToOrdersDto(final List<Order> orders) {
      final List<OrderDto> ordersDto = new ArrayList<>();

    for (final Order order : orders) {
      ordersDto.add(mapOrderToOrderDto(order));
    }

    ordersDto.sort(Comparator.comparing(OrderDto::id));
    log.info("Orders' list mapped to orders' DTOs list");
    return ordersDto;
  }

  public OrderDto mapOrderToOrderDto(final Order order) {
      final List<String> booksNames = new ArrayList<>();

    for (final Book book : order.getBooks()) {
      booksNames.add(book.getName());
    }

    log.info("Order mapped to order DTO");
    return new OrderDto(order.getId(), clientMapper.mapClientToClientDto(order.getClient()),
                        booksNames, order.getIssueDate(), order.getDueDate());
  }
}

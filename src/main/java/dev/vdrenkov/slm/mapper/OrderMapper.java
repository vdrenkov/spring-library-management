package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Book;
import dev.vdrenkov.slm.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * OrderMapper component.
 */
public final class OrderMapper {
    private static final Logger log = LoggerFactory.getLogger(OrderMapper.class);

    private OrderMapper() {
        /* This utility class should not be instantiated */
    }

    /**
     * Handles mapOrdersToOrdersDto operation.
     *
     * @param orders
     *     Order entities to map.
     * @return List of order DTOs.
     */
    public static List<OrderDto> mapOrdersToOrdersDto(final List<Order> orders) {
        final List<OrderDto> ordersDto = new ArrayList<>();

        for (final Order order : orders) {
            ordersDto.add(mapOrderToOrderDto(order));
        }

        ordersDto.sort(Comparator.comparing(OrderDto::id));
        log.debug("Orders' list mapped to orders' DTOs list");
        return ordersDto;
    }

    /**
     * Handles mapOrderToOrderDto operation.
     *
     * @param order
     *     Order entity value.
     * @return Resulting order DTO value.
     */
    public static OrderDto mapOrderToOrderDto(final Order order) {
        final List<String> booksNames = new ArrayList<>();

        for (final Book book : order.getBooks()) {
            booksNames.add(book.getName());
        }

        log.debug("Order mapped to order DTO");
        return new OrderDto(order.getId(), ClientMapper.mapClientToClientDto(order.getClient()), booksNames,
            order.getIssueDate(), order.getDueDate());
    }
}

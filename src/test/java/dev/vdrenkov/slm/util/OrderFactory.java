package dev.vdrenkov.slm.util;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Order;
import dev.vdrenkov.slm.request.OrderRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.LOCAL_DATE;

/**
 * Test factory for creating default {@link Order}, {@link OrderDto}, and {@link OrderRequest} instances.
 */
public final class OrderFactory {

    /**
     * Utility class constructor.
     */
    private OrderFactory() {
        throw new IllegalStateException("Utility class. Must not be instantiated!");
    }

    /**
     * Creates a default {@link Order} test entity.
     *
     * @return Default order entity.
     */
    public static Order getDefaultOrder() {
        return new Order(ID, ClientFactory.getDefaultClient(), BookFactory.getDefaultBooksList(), LOCAL_DATE,
            LOCAL_DATE);
    }

    /**
     * Creates a singleton list containing the default order entity.
     *
     * @return Singleton list of orders.
     */
    public static List<Order> getDefaultOrdersList() {
        return Collections.singletonList(getDefaultOrder());
    }

    /**
     * Creates a default {@link OrderDto}.
     *
     * @return Default order DTO.
     */
    public static OrderDto getDefaultOrderDto() {
        return new OrderDto(ID, ClientFactory.getDefaultClientDto(), BookFactory.getDefaultBooksNamesList(), LOCAL_DATE,
            LOCAL_DATE);
    }

    /**
     * Creates a singleton list containing the default order DTO.
     *
     * @return Singleton list of order DTOs.
     */
    public static List<OrderDto> getDefaultOrdersDtoList() {
        return Collections.singletonList(getDefaultOrderDto());
    }

    /**
     * Creates a default {@link OrderRequest}.
     *
     * @return Default order request.
     */
    public static OrderRequest getDefaultOrderRequest() {
        return new OrderRequest(ID, BookFactory.getDefaultBooksIdsList());
    }
}

package dev.vdrenkov.biblium.mapper;

import dev.vdrenkov.biblium.dto.OrderDto;
import dev.vdrenkov.biblium.util.factory.OrderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @Test
    void testMapBooksToBooksDto() {
        final List<OrderDto> ordersDto = OrderMapper.mapOrdersToOrdersDto(OrderFactory.getDefaultOrdersList());

        assertNotNull(ordersDto);
    }

    @Test
    void testMapClientToClientDto() {
        final OrderDto orderDto = OrderMapper.mapOrderToOrderDto(OrderFactory.getDefaultOrder());

        assertNotNull(orderDto);
    }
}

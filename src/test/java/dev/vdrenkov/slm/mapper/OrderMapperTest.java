package dev.vdrenkov.slm.mapper;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.util.ClientFactory;
import dev.vdrenkov.slm.util.OrderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private OrderMapper mapper;

    @Test
    void testMapBooksToBooksDto() {
        when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

        List<OrderDto> ordersDto = mapper.mapOrdersToOrdersDto(OrderFactory.getDefaultOrdersList());

        assertNotNull(ordersDto);
    }

    @Test
    void testMapClientToClientDto() {
        when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

        OrderDto orderDto = mapper.mapOrderToOrderDto(OrderFactory.getDefaultOrder());

        assertNotNull(orderDto);
    }
}


package bg.vdrenkov.mapper;

import bg.vdrenkov.dto.OrderDto;
import bg.vdrenkov.test.util.ClientFactory;
import bg.vdrenkov.test.util.OrderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

  @Mock
  private ClientMapper clientMapper;

  @InjectMocks
  private OrderMapper mapper;

  @Test
  public void testMapBooksToBooksDto() {
    when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

    List<OrderDto> ordersDto = mapper.mapOrdersToOrdersDto(OrderFactory.getDefaultOrdersList());

    assertNotNull(ordersDto);
  }

  @Test
  public void testMapClientToClientDto() {
    when(clientMapper.mapClientToClientDto(any())).thenReturn(ClientFactory.getDefaultClientDto());

    OrderDto orderDto = mapper.mapOrderToOrderDto(OrderFactory.getDefaultOrder());

    assertNotNull(orderDto);
  }
}
package bg.vdrenkov.test.util;

import bg.vdrenkov.dto.OrderDto;
import bg.vdrenkov.entity.Order;
import bg.vdrenkov.request.OrderRequest;

import java.util.Collections;
import java.util.List;

import static bg.vdrenkov.util.Constants.ID;
import static bg.vdrenkov.util.Constants.LOCAL_DATE;

public final class OrderFactory {

  private OrderFactory() {
    throw new IllegalStateException();
  }

  public static Order getDefaultOrder() {
    return new Order(ID, ClientFactory.getDefaultClient(), BookFactory.getDefaultBooksList(), LOCAL_DATE, LOCAL_DATE);
  }

  public static List<Order> getDefaultOrdersList() {
    return Collections.singletonList(getDefaultOrder());
  }

  public static OrderDto getDefaultOrderDto() {
    return new OrderDto(ID, ClientFactory.getDefaultClientDto(), BookFactory.getDefaultBooksNamesList(), LOCAL_DATE,
                        LOCAL_DATE);
  }

  public static List<OrderDto> getDefaultOrdersDtoList() {
    return Collections.singletonList(getDefaultOrderDto());
  }

  public static OrderRequest getDefaultOrderRequest() {
    return new OrderRequest(ID, BookFactory.getDefaultBooksIdsList());
  }
}

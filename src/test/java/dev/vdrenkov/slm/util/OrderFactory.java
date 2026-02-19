package dev.vdrenkov.slm.util;

import dev.vdrenkov.slm.dto.OrderDto;
import dev.vdrenkov.slm.entity.Order;
import dev.vdrenkov.slm.request.OrderRequest;

import java.util.Collections;
import java.util.List;

import static dev.vdrenkov.slm.util.Constants.ID;
import static dev.vdrenkov.slm.util.Constants.LOCAL_DATE;

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


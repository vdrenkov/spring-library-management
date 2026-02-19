package dev.vdrenkov.controller;

import dev.vdrenkov.dto.OrderDto;
import dev.vdrenkov.entity.Order;
import dev.vdrenkov.request.OrderRequest;
import dev.vdrenkov.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  private final String URI = "/orders";
  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping(URI)
  public ResponseEntity<Void> addOrder(@RequestBody @Valid OrderRequest orderRequest) {
    Order order = orderService.addOrder(orderRequest);

    URI location = UriComponentsBuilder.fromUriString("/orders/{id}")
                                       .buildAndExpand(order.getId())
                                       .toUri();
    log.info("A new order was added");
    return ResponseEntity.created(location).build();
  }

  @GetMapping(URI)
  public ResponseEntity<List<OrderDto>> getAllOrders() {
    log.info("All orders were requested from the database");
    return ResponseEntity.ok(orderService.getAllOrdersDto());
  }

  @GetMapping("/clients/{clientId}" + URI)
  public ResponseEntity<List<OrderDto>> getAllOrdersByClient(@PathVariable int clientId) {
    log.info(String.format("All orders with client id %d were requested from the database", clientId));
    return ResponseEntity.ok(orderService.getAllOrdersDtoByClient(clientId));
  }

  @GetMapping(value = URI, params = {"choice", "date"})
  public ResponseEntity<List<OrderDto>> getALlOrdersByDate(@RequestParam int choice, @RequestParam String date) {
    log.info("All orders by date were requested from the database");
    return ResponseEntity.ok(orderService.getAllOrdersDtoByDate(choice, date));
  }

  @GetMapping(URI + "/{id}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable int id) {
    log.info(String.format("Order with id %d was requested from the database", id));
    return ResponseEntity.ok(orderService.getOrderDtoById(id));
  }

  @PutMapping(URI + "/{id}")
  public ResponseEntity<OrderDto> extendOrderDueByDate(
    @PathVariable int id, @RequestParam int choice, @RequestParam int period,
    @RequestParam(required = false) boolean returnOld) {

    OrderDto oldOrder = orderService.extendOrderDueByDate(id, choice, period);

    log.info(String.format("Order with id %d was requested to be updated", id));

    if (returnOld) {
      return ResponseEntity.ok(oldOrder);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}


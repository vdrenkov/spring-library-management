package dev.vdrenkov.biblium.controller;

import dev.vdrenkov.biblium.dto.OrderDto;
import dev.vdrenkov.biblium.entity.Order;
import dev.vdrenkov.biblium.request.OrderRequest;
import dev.vdrenkov.biblium.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * OrderController component.
 */
@RestController
@Validated
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private static final String URI = "/orders";

    private final OrderService orderService;

    /**
     * Handles OrderController operation.
     *
     * @param orderService
     *     Service dependency used by this component.
     */
    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Handles addOrder operation.
     *
     * @param orderRequest
     *     Request payload with input data.
     * @return Response entity containing the operation result.
     */
    @PostMapping(URI)
    public ResponseEntity<Void> addOrder(@RequestBody @Valid final OrderRequest orderRequest) {
        final Order order = orderService.addOrder(orderRequest);

        final URI location = UriComponentsBuilder.fromUriString("/orders/{id}").buildAndExpand(order.getId()).toUri();
        log.info("A new order added");
        return ResponseEntity.created(location).build();
    }

    /**
     * Handles getAllOrders operation.
     *
     * @return Response entity containing the requested data.
     */
    @GetMapping(URI)
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.info("All orders requested from the database");
        return ResponseEntity.ok(orderService.getAllOrdersDto());
    }

    /**
     * Handles getAllOrdersByClient operation.
     *
     * @param clientId
     *     Identifier of the target entity.
     * @return Response entity containing the requested data.
     */
    @GetMapping("/clients/{clientId}" + URI)
    public ResponseEntity<List<OrderDto>> getAllOrdersByClient(@PathVariable final int clientId) {
        log.info("All orders with client ID {} requested from the database.", clientId);
        return ResponseEntity.ok(orderService.getAllOrdersDtoByClient(clientId));
    }

    /**
     * Handles getALlOrdersByDate operation.
     *
     * @param choice
     *     Date filter mode (1-6).
     * @param date
     *     Date value in {@code yyyy-MM-dd} format.
     * @return Response containing orders that match the requested date filter.
     */
    @GetMapping(value = URI, params = { "choice", "date" })
    public ResponseEntity<List<OrderDto>> getALlOrdersByDate(
        @RequestParam @Min(value = 1, message = "Choice must be between 1 and 6") @Max(value = 6,
            message = "Choice must be between 1 and 6") final int choice, @RequestParam final String date) {
        log.info("All orders by date requested from the database.");
        return ResponseEntity.ok(orderService.getAllOrdersDtoByDate(choice, date));
    }

    /**
     * Handles getOrderById operation.
     *
     * @param id
     *     Identifier of the target entity.
     * @return Response entity containing the requested data.
     */
    @GetMapping(URI + "/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable final int id) {
        log.info("Order with an ID {} requested from the database.", id);
        return ResponseEntity.ok(orderService.getOrderDtoById(id));
    }

    /**
     * Handles extendOrderDueByDate operation.
     *
     * @param id
     *     Order identifier.
     * @param choice
     *     Extension unit selector (1=days, 2=weeks, 3=months).
     * @param period
     *     Number of units to extend by.
     * @param returnOld
     *     Whether to return the previous order snapshot.
     * @return Response with previous order payload or no-content status.
     */
    @PutMapping(URI + "/{id}")
    public ResponseEntity<OrderDto> extendOrderDueByDate(@PathVariable final int id,
        @RequestParam @Min(value = 1, message = "Choice must be between 1 and 3") @Max(value = 3,
            message = "Choice must be between 1 and 3") final int choice,
        @RequestParam @Positive(message = "Period must be a positive number") final int period,
        @RequestParam(required = false) final Boolean returnOld) {

        final OrderDto oldOrder = orderService.extendOrderDueByDate(id, choice, period);

        log.info("Order with ID {} requested to be updated.", id);

        if (Boolean.TRUE.equals(returnOld)) {
            return ResponseEntity.ok(oldOrder);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

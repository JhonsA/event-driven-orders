package cl.kafka.orderservice.controller;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.dto.OrderResponse;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request
    ) {
        Order order = orderService.createOrder(request);
        URI location = URI.create("/orders/" + order.id());

        OrderResponse response = OrderResponse.from(order);

        return ResponseEntity
                .created(location)
                .body(response);
    }

}

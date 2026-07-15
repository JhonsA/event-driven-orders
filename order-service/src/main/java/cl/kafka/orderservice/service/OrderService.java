package cl.kafka.orderservice.service;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.model.Order;

public class OrderService {
    public Order createOrder(CreateOrderRequest request) {
        return new Order(
                request.customerId(),
                request.productId(),
                request.quantity(),
                request.unitPrice(),
                OrderStatus.CREATED
        );
    }
}

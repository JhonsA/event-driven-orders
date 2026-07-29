package cl.kafka.orderservice.dto;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;

import java.math.BigDecimal;

public record OrderResponse(
        String id,
        String customerId,
        String productId,
        int quantity,
        BigDecimal unitPrice,
        OrderStatus status
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.id(),
                order.customerId(),
                order.productId(),
                order.quantity(),
                order.unitPrice(),
                order.status()
        );
    }
}

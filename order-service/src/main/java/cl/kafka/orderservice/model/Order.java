package cl.kafka.orderservice.model;

import java.math.BigDecimal;

public record Order(
        String customerId,
        String productId,
        int quantity,
        BigDecimal unitPrice,
        OrderStatus status
) {
}

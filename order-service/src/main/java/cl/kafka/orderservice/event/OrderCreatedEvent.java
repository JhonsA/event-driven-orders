package cl.kafka.orderservice.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        String productId,
        int quantity,
        BigDecimal unitPrice
) {
}

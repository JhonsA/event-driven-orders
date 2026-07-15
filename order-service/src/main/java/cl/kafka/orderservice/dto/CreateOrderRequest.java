package cl.kafka.orderservice.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String customerId,
        String productId,
        int quantity,
        BigDecimal unitPrice
) {
}

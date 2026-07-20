package cl.kafka.orderservice.service;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    @Test
    void shouldCreateValidOrder() {
        // Arrange: preparar datos
        OrderService orderService = new OrderService();

        CreateOrderRequest request = new CreateOrderRequest(
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990")
        );

        // Act: ejecutar lo que se quiere probar
        Order order = orderService.createOrder(request);

        // Assert: verificar resultado
        assertEquals(OrderStatus.CREATED, order.status());
        assertEquals("customer-123", order.customerId());
        assertEquals("product-456", order.productId());
        assertEquals(2, order.quantity());
        assertEquals(new BigDecimal("19990"), order.unitPrice());
    }

}

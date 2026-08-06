package cl.kafka.orderservice.repository.mapper;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.repository.entity.OrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPersistenceMapperTest {

    private final OrderPersistenceMapper mapper = new OrderPersistenceMapper();

    @Test
    void toEntityShouldMapDomainOrder() {
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        OrderEntity entity = mapper.toEntity(order);

        assertEquals(order.id(), entity.getId());
        assertEquals(order.customerId(), entity.getCustomerId());
        assertEquals(order.productId(), entity.getProductId());
        assertEquals(order.quantity(), entity.getQuantity());
        assertEquals(order.unitPrice(), entity.getUnitPrice());
        assertEquals(order.status(), entity.getStatus());
    }

    @Test
    void toDomainShouldMapOrderEntity() {
        OrderEntity entity = new OrderEntity(
                "order-123",
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        Order order = mapper.toDomain(entity);

        assertEquals(entity.getId(), order.id());
        assertEquals(entity.getCustomerId(), order.customerId());
        assertEquals(entity.getProductId(), order.productId());
        assertEquals(entity.getQuantity(), order.quantity());
        assertEquals(entity.getUnitPrice(), order.unitPrice());
        assertEquals(entity.getStatus(), order.status());
    }
}

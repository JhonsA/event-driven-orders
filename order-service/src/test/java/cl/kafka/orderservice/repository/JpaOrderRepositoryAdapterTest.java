package cl.kafka.orderservice.repository;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.repository.entity.OrderEntity;
import cl.kafka.orderservice.repository.jpa.SpringDataOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class JpaOrderRepositoryAdapterTest {

    private final SpringDataOrderRepository springDataRepository =
            mock(SpringDataOrderRepository.class);

    private final JpaOrderRepositoryAdapter adapter =
            new JpaOrderRepositoryAdapter(springDataRepository);

    @Test
    void saveShouldMapOrderAndDelegateToSpringDataRepository() {
        // Arrange
        Order order = new Order(
                "order-123",
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        ArgumentCaptor<OrderEntity> entityCaptor =
                ArgumentCaptor.forClass(OrderEntity.class);

        // Act
        adapter.save(order);

        // Assert
        verify(springDataRepository)
                .save(entityCaptor.capture());

        OrderEntity savedEntity = entityCaptor.getValue();

        // Assertions
        assertEquals(order.id(), savedEntity.getId());
        assertEquals(order.customerId(), savedEntity.getCustomerId());
        assertEquals(order.productId(), savedEntity.getProductId());
        assertEquals(order.quantity(), savedEntity.getQuantity());
        assertEquals(order.unitPrice(), savedEntity.getUnitPrice());
        assertEquals(order.status(), savedEntity.getStatus());
    }

    @Test
    void findByIdShouldMapEntityToDomainOrder() {
        // Arrange
        String orderId = "order-123";

        OrderEntity entity = new OrderEntity(
                orderId,
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(springDataRepository.findById(orderId))
                .thenReturn(Optional.of(entity));

        // Act
        Optional<Order> result = adapter.findById(orderId);

        // Assert
        verify(springDataRepository)
                .findById(orderId);

        assertTrue(result.isPresent());

        Order order = result.get();

        assertEquals(entity.getId(), order.id());
        assertEquals(entity.getCustomerId(), order.customerId());
        assertEquals(entity.getProductId(), order.productId());
        assertEquals(entity.getQuantity(), order.quantity());
        assertEquals(entity.getUnitPrice(), order.unitPrice());
        assertEquals(entity.getStatus(), order.status());
    }

}

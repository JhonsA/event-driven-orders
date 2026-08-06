package cl.kafka.orderservice.repository;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.repository.entity.OrderEntity;
import cl.kafka.orderservice.repository.jpa.SpringDataOrderRepository;
import cl.kafka.orderservice.repository.mapper.OrderPersistenceMapper;
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

    private final OrderPersistenceMapper orderPersistenceMapper =
            mock(OrderPersistenceMapper.class);

    private final JpaOrderRepositoryAdapter adapter =
            new JpaOrderRepositoryAdapter(springDataRepository, orderPersistenceMapper);

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

        OrderEntity entity = new OrderEntity(
                "order-123",
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(orderPersistenceMapper.toEntity(order))
                .thenReturn(entity);

        // Act
        adapter.save(order);

        // Assert
        verify(orderPersistenceMapper)
                .toEntity(order);

        verify(springDataRepository)
                .save(entity);
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

        Order mappedOrder = new Order(
                orderId,
                "customer-123",
                "product-456",
                2,
                new BigDecimal("19990"),
                OrderStatus.CREATED
        );

        when(springDataRepository.findById(orderId))
                .thenReturn(Optional.of(entity));

        when(orderPersistenceMapper.toDomain(entity))
                .thenReturn(mappedOrder);

        // Act
        Optional<Order> result = adapter.findById(orderId);

        // Assert
        verify(springDataRepository)
                .findById(orderId);

        verify(orderPersistenceMapper)
                .toDomain(entity);

        assertEquals(Optional.of(mappedOrder), result);
    }

}

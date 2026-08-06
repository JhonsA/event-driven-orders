package cl.kafka.orderservice.repository.mapper;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.repository.entity.OrderEntity;

public class OrderPersistenceMapper {

    public OrderEntity toEntity(Order order) {
        return new OrderEntity(
                order.id(),
                order.customerId(),
                order.productId(),
                order.quantity(),
                order.unitPrice(),
                order.status()
        );
    }

    public Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getStatus()
        );
    }
}

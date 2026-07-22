package cl.kafka.orderservice.port.out;

import cl.kafka.orderservice.model.Order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(String id);
}

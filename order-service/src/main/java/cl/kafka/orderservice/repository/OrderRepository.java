package cl.kafka.orderservice.repository;

import cl.kafka.orderservice.model.Order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(String id);
}

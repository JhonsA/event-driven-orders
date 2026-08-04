package cl.kafka.orderservice.repository.jpa;

import cl.kafka.orderservice.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, String> {
}

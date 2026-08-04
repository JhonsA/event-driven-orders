package cl.kafka.orderservice.repository;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.port.out.OrderRepository;
import cl.kafka.orderservice.repository.entity.OrderEntity;
import cl.kafka.orderservice.repository.jpa.SpringDataOrderRepository;

import java.util.Optional;

public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataRepository;

    public JpaOrderRepositoryAdapter(SpringDataOrderRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public void save(Order order) {
        OrderEntity entity = new OrderEntity(
                order.id(),
                order.customerId(),
                order.productId(),
                order.quantity(),
                order.unitPrice(),
                order.status()
        );

        springDataRepository.save(entity);
    }

    @Override
    public Optional<Order> findById(String id) {
        return springDataRepository.findById(id)
                .map(entity -> new Order(
                        entity.getId(),
                        entity.getCustomerId(),
                        entity.getProductId(),
                        entity.getQuantity(),
                        entity.getUnitPrice(),
                        entity.getStatus()
                ));
    }
}

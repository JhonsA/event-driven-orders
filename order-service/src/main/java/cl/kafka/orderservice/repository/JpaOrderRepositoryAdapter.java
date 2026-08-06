package cl.kafka.orderservice.repository;

import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.port.out.OrderRepository;
import cl.kafka.orderservice.repository.jpa.SpringDataOrderRepository;
import cl.kafka.orderservice.repository.mapper.OrderPersistenceMapper;

import java.util.Optional;

public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataRepository;
    private final OrderPersistenceMapper mapper;

    public JpaOrderRepositoryAdapter(SpringDataOrderRepository springDataRepository, OrderPersistenceMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Order order) {
        springDataRepository.save(mapper.toEntity(order));
    }

    @Override
    public Optional<Order> findById(String id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }
}

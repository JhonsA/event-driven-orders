package cl.kafka.orderservice.service;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.exception.OrderNotFoundException;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.repository.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderIdGenerator orderIdGenerator;

    public OrderService (OrderRepository orderRepository, OrderIdGenerator orderIdGenerator) {
        this.orderRepository = orderRepository;
        this.orderIdGenerator = orderIdGenerator;
    }

    public Order createOrder(CreateOrderRequest request) {
        String orderId = orderIdGenerator.generate();
        Order order = new Order(
                orderId,
                request.customerId(),
                request.productId(),
                request.quantity(),
                request.unitPrice(),
                OrderStatus.CREATED
        );

        orderRepository.save(order);
        return order;
    }

    public Order findById(String id) {
        return orderRepository.findById(id).
                orElseThrow(() -> new OrderNotFoundException(id));
    }
}

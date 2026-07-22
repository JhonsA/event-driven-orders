package cl.kafka.orderservice.service;

import cl.kafka.orderservice.dto.CreateOrderRequest;
import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.exception.OrderNotFoundException;
import cl.kafka.orderservice.model.Order;
import cl.kafka.orderservice.model.OrderStatus;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import cl.kafka.orderservice.port.out.OrderIdGenerator;
import cl.kafka.orderservice.port.out.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderIdGenerator orderIdGenerator;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService (OrderRepository orderRepository, OrderIdGenerator orderIdGenerator, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderIdGenerator = orderIdGenerator;
        this.orderEventPublisher = orderEventPublisher;
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
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                order.id(),
                order.customerId(),
                order.productId(),
                order.quantity(),
                order.unitPrice()
        );
        orderEventPublisher.publishOrderCreated(orderCreatedEvent);
        return order;
    }

    public Order findById(String id) {
        return orderRepository.findById(id).
                orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void cancelOrder(String id) {
        Order order = findById(id);
        order.cancel();
        orderRepository.save(order);
    }

    public void payOrder(String id) {
        Order order = findById(id);
        order.pay();
        orderRepository.save(order);
    }
}

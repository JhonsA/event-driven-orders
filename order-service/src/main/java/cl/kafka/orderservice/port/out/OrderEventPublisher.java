package cl.kafka.orderservice.port.out;

import cl.kafka.orderservice.event.OrderCreatedEvent;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
}

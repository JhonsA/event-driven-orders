package cl.kafka.orderservice.port.out;

import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.event.OrderPaidEvent;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);

    void publishOrderPaid(OrderPaidEvent event);
}

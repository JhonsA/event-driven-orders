package cl.kafka.orderservice.kafka.producer;

import cl.kafka.orderservice.config.OrderTopicsProperties;
import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.event.OrderPaidEvent;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderTopicsProperties topics;

    public KafkaOrderEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            OrderTopicsProperties topics
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topics = topics;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(topics.created(), event);
    }

    @Override
    public void publishOrderPaid(OrderPaidEvent event) {
        kafkaTemplate.send(topics.paid(), event);
    }
}

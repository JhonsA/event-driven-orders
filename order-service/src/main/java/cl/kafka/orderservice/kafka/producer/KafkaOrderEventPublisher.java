package cl.kafka.orderservice.kafka.producer;

import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final String topicName;

    public KafkaOrderEventPublisher(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(topicName, event);
    }
}

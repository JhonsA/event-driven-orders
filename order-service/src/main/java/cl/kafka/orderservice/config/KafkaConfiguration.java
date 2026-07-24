package cl.kafka.orderservice.config;

import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.kafka.producer.KafkaOrderEventPublisher;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {

    @Bean
    OrderEventPublisher orderEventPublisher(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            @Value("${app.kafka.topics.order-created}") String topicName
    ) {
        return new KafkaOrderEventPublisher(
                kafkaTemplate,
                topicName
        );
    }
}

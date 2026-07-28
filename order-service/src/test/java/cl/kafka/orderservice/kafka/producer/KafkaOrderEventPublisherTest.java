package cl.kafka.orderservice.kafka.producer;

import cl.kafka.orderservice.config.OrderTopicsProperties;
import cl.kafka.orderservice.event.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class KafkaOrderEventPublisherTest {

    @Test
    void shouldPublishOrderCreatedEventToConfiguredTopic() {
        // Arrange
        KafkaTemplate<String, Object> kafkaTemplate =
                mock(KafkaTemplate.class);

        OrderTopicsProperties orderTopicsProperties =
                new OrderTopicsProperties(
                        "orders.created",
                        "orders.paid"
                );

        KafkaOrderEventPublisher publisher =
                new KafkaOrderEventPublisher(
                        kafkaTemplate,
                        orderTopicsProperties
                );

        OrderCreatedEvent event = new OrderCreatedEvent(
                "order-123",
                "customer-123",
                "product-123",
                2,
                new BigDecimal("15000")
        );

        // Act
        publisher.publishOrderCreated(event);

        // Assert
        verify(kafkaTemplate)
                .send("orders.created", event);
    }

}

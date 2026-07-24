package cl.kafka.orderservice.config;

import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class KafkaConfigurationTest {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate =
            mock(KafkaTemplate.class);

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withUserConfiguration(KafkaConfiguration.class)
                    .withBean(KafkaTemplate.class, () -> { return kafkaTemplate; })
                    .withPropertyValues(
                            "app.kafka.topics.order-created=orders.created"
                    );

    @Test
    void shouldConfigureOrderEventPublisherWithConfiguredTopic() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent(
                "order-123",
                "customer-123",
                "product-123",
                2,
                new BigDecimal("15000")
        );

        // Act + Assert
        contextRunner.run(context -> {
            OrderEventPublisher publisher =
                    context.getBean(OrderEventPublisher.class);

            publisher.publishOrderCreated(event);

            verify(kafkaTemplate)
                    .send("orders.created", event);
        });
    }

}

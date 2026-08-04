package cl.kafka.orderservice.kafka.producer;

import cl.kafka.orderservice.config.KafkaConfiguration;
import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = {
                KafkaAutoConfiguration.class,
                KafkaConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "app.kafka.topics.order.created=orders.created",
                "app.kafka.topics.order.paid=orders.paid"
        }
)
@EmbeddedKafka(
        partitions = 1,
        topics = "orders.created",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
class KafkaOrderEventPublisherIntegrationTest {

    @Autowired
    private OrderEventPublisher publisher;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, OrderCreatedEvent> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps(
                        "test-group",
                        "true",
                        embeddedKafkaBroker
                );

        ConsumerFactory<String, OrderCreatedEvent> consumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        consumerProps,
                        new StringDeserializer(),
                        new JsonDeserializer<>(OrderCreatedEvent.class)
                );

        consumer = consumerFactory.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(
                consumer,
                "orders.created"
        );
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void shouldPublishOrderCreatedEvent() {
        OrderCreatedEvent event = new OrderCreatedEvent(
                "order-1",
                "customer-1",
                "product-1",
                2,
                BigDecimal.valueOf(1000)
        );

        publisher.publishOrderCreated(event);

        ConsumerRecord<String, OrderCreatedEvent> record =
                KafkaTestUtils.getSingleRecord(
                        consumer,
                        "orders.created"
                );

        assertEquals(event, record.value());
    }
}
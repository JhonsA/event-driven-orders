package cl.kafka.orderservice.kafka.producer;

import cl.kafka.orderservice.event.OrderCreatedEvent;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import cl.kafka.orderservice.service.OrderService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = "orders.created",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
class KafkaOrderEventPublisherIntegrationTest {

    @MockitoBean
    private OrderService orderService;

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

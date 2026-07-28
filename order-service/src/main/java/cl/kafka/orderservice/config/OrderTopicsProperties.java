package cl.kafka.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics.order")
public record OrderTopicsProperties(
        String created,
        String paid
) {
}

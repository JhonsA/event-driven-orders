package cl.kafka.orderservice.config;

import cl.kafka.orderservice.kafka.producer.KafkaOrderEventPublisher;
import cl.kafka.orderservice.port.out.OrderEventPublisher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableConfigurationProperties(OrderTopicsProperties.class)
public class KafkaConfiguration {

    /*
        Cuando Spring levanta el contexto de aplicación, procesa KafkaConfiguration, ejecuta el método anotado con @Bean
        y registra en el contenedor un bean de tipo OrderEventPublisher, cuya implementación concreta es KafkaOrderEventPublisher.
        Spring registra una implementación del puerto OrderEventPublisher como bean dentro del ApplicationContext.
     */
    @Bean
    OrderEventPublisher orderEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            OrderTopicsProperties topics
    ) {
        return new KafkaOrderEventPublisher(
                kafkaTemplate,
                topics
        );
    }
}

package Mend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static final String SCAN_QUEUE = "scanQueue";
    public static final String DLX_QUEUE = "dlxQueue";
    public static final String SCAN_EXCHANGE = "scanExchange";
    public static final String DLX_EXCHANGE = "dlxExchange";

    // Durable Scan Queue with TTL
    @Bean
    public Queue scanQueue() {
        log.info("Creating Scan Queue");

        return QueueBuilder.durable(SCAN_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-message-ttl", 60000)
                .build();
    }

    // Dead Letter Queue
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE).build();
    }

    // Exchange for Scans
    @Bean
    public DirectExchange scanExchange() {
        return new DirectExchange(SCAN_EXCHANGE);
    }

    // Dead Letter Exchange (DLX)
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Binding bindingScanQueue(Queue scanQueue, DirectExchange scanExchange) {
        return BindingBuilder.bind(scanQueue).to(scanExchange).with(SCAN_QUEUE);
    }

    @Bean
    public Binding bindingDLXQueue(Queue dlxQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with(DLX_QUEUE);
    }
}

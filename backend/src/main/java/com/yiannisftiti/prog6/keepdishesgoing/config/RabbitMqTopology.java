package com.yiannisftiti.prog6.keepdishesgoing.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTopology {

    public static final String KDG_EVENTS_EXCHANGE = "kdg.events";
    public static final String DELIVERY_ORDER_EVENTS_QUEUE = "delivery.order.events";

    @Bean
    TopicExchange kdgEventsExchange() {
        return ExchangeBuilder.topicExchange(KDG_EVENTS_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    Queue deliveryOrderEventsQueue() {
        return QueueBuilder.durable(DELIVERY_ORDER_EVENTS_QUEUE).build();
    }

    @Bean
    Binding bindDeliveryQueueToExchange() {
        return BindingBuilder
                .bind(deliveryOrderEventsQueue())
                .to(kdgEventsExchange())
                .with("delivery.*.order.*.v1");
    }
}

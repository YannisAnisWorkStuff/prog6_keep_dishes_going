package com.yiannisftiti.prog6.keepdishesgoing.delivery.adapter.in.listener;

import com.yiannisftiti.prog6.keepdishesgoing.config.RabbitMqTopology;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.delivery.DeliveryOrderDeliveredEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.delivery.DeliveryOrderPickedUpEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.adapter.out.SpringDomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class DeliveryMqListener {

    private static final Logger log = LoggerFactory.getLogger(DeliveryMqListener.class);
    private final SpringDomainEventPublisher eventPublisher;

    public DeliveryMqListener(SpringDomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = RabbitMqTopology.DELIVERY_ORDER_EVENTS_QUEUE)
    public void handleDeliveryUpdate(Map<String, Object> message) {
        log.info("Received message from delivery-service: {}", message);

        try {
            String type = (String) message.get("type");
            UUID orderId = UUID.fromString((String) message.get("orderId"));
            UUID restaurantId = UUID.fromString((String) message.get("restaurantId"));

            switch (type) {
                case "PICKED_UP" ->
                        eventPublisher.publishEvent(new DeliveryOrderPickedUpEvent(orderId, restaurantId));
                case "DELIVERED" ->
                        eventPublisher.publishEvent(new DeliveryOrderDeliveredEvent(orderId, restaurantId));
                default -> log.warn("Unknown delivery event type: {}", type);
            }

        } catch (Exception e) {
            log.error("Error when deliverying message: {}", e.getMessage(), e);
        }
    }
}
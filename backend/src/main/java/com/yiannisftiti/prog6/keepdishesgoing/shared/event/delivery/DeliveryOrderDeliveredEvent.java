package com.yiannisftiti.prog6.keepdishesgoing.shared.event.delivery;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryOrderDeliveredEvent(UUID orderId, UUID restaurantId) implements DomainEvent {
    @Override
    public LocalDateTime eventPit() {
        return LocalDateTime.now();
    }
}

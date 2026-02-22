package com.yiannisftiti.prog6.keepdishesgoing.shared.event.order;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRejectedEvent(UUID orderId, UUID restaurantId, String reason) implements DomainEvent {

    @Override
    public LocalDateTime eventPit() {
        return LocalDateTime.now();
    }
}
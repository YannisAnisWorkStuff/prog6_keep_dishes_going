package com.yiannisftiti.prog6.keepdishesgoing.shared.event.order;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, UUID restaurantId, UUID customerId, BigDecimal total) implements DomainEvent {

    @Override
    public LocalDateTime eventPit() {
        return LocalDateTime.now();
    }
}
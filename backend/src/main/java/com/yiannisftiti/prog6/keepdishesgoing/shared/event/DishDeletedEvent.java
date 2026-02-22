package com.yiannisftiti.prog6.keepdishesgoing.shared.event;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishDeletedEvent(LocalDateTime eventPit, UUID restaurantId, UUID dish)
        implements DomainEvent {

    public DishDeletedEvent(UUID restaurantId, UUID dishId) {
        this(LocalDateTime.now(), restaurantId, dishId);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
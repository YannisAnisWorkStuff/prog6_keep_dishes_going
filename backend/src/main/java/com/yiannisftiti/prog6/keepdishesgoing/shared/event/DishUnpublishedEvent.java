package com.yiannisftiti.prog6.keepdishesgoing.shared.event;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishUnpublishedEvent(LocalDateTime eventPit, UUID restaurantId, UUID dishId, String dishName) implements DomainEvent {

    public DishUnpublishedEvent(UUID restaurantId, UUID dishId, String dishName) {
        this(LocalDateTime.now(), restaurantId, dishId, dishName);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}

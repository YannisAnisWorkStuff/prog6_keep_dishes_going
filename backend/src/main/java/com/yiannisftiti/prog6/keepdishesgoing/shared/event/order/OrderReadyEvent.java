package com.yiannisftiti.prog6.keepdishesgoing.shared.event.order;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.ready.v1'}")
public record OrderReadyEvent(UUID orderId, UUID restaurantId) implements DomainEvent {
    @Override
    public LocalDateTime eventPit() {
        return LocalDateTime.now();
    }
}
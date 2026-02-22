package com.yiannisftiti.prog6.keepdishesgoing.shared.event.order;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.accepted.v1'}")
public record OrderAcceptedEvent(UUID orderId, UUID restaurantId, UUID customerId) implements DomainEvent {

    @Override
    public LocalDateTime eventPit() {
        return LocalDateTime.now();
    }
}
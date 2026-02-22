package com.yiannisftiti.prog6.keepdishesgoing.shared.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.util.List;

public interface PublishDomainEventsPort {
    void publish(List<DomainEvent> events);
    void publishEvent(DomainEvent event);
}
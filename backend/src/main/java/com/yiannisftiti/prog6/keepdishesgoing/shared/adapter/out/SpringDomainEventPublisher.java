package com.yiannisftiti.prog6.keepdishesgoing.shared.adapter.out;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringDomainEventPublisher implements PublishDomainEventsPort {

    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(publisher::publishEvent);
    }

    @Override
    public void publishEvent(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
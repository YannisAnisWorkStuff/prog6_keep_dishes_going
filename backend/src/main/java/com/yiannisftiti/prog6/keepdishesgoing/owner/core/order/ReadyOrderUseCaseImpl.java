package com.yiannisftiti.prog6.keepdishesgoing.owner.core.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.ReadyOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.LoadIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.SaveIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderReadyEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReadyOrderUseCaseImpl implements ReadyOrderUseCase {

    private final LoadIncomingOrderPort loadIncomingOrderPort;
    private final SaveIncomingOrderPort saveIncomingOrderPort;
    private final PublishDomainEventsPort eventPublisher;

    public ReadyOrderUseCaseImpl(LoadIncomingOrderPort loadIncomingOrderPort, SaveIncomingOrderPort saveIncomingOrderPort,
                                  PublishDomainEventsPort eventPublisher
    ) {
        this.loadIncomingOrderPort = loadIncomingOrderPort;
        this.saveIncomingOrderPort = saveIncomingOrderPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void markReady(UUID restaurantId, UUID orderId) {
        IncomingOrderProjection order = loadIncomingOrderPort.load(restaurantId, orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        order.ready();
        saveIncomingOrderPort.save(order);

        eventPublisher.publishEvent(new OrderReadyEvent(orderId, restaurantId));
    }
}
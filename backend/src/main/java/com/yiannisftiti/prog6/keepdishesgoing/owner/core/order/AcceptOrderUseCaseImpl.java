package com.yiannisftiti.prog6.keepdishesgoing.owner.core.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.AcceptOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.LoadIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.SaveIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderAcceptedEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AcceptOrderUseCaseImpl implements AcceptOrderUseCase {

    private final LoadIncomingOrderPort loadIncomingOrderPort;
    private final SaveIncomingOrderPort saveIncomingOrderPort;
    private final PublishDomainEventsPort eventPublisher;

    public AcceptOrderUseCaseImpl(LoadIncomingOrderPort loadIncomingOrderPort, SaveIncomingOrderPort saveIncomingOrderPort,
                                  PublishDomainEventsPort eventPublisher) {
        this.loadIncomingOrderPort = loadIncomingOrderPort;
        this.saveIncomingOrderPort = saveIncomingOrderPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void acceptOrder(UUID restaurantId, UUID orderId) {
        IncomingOrderProjection order = loadIncomingOrderPort.load(restaurantId, orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));// I think i'll just keep it at that instead of creating a custom exception for this too
        order.accept();
        saveIncomingOrderPort.save(order);

        eventPublisher.publishEvent(new OrderAcceptedEvent(orderId, restaurantId, order.getCustomerId()));
    }
}

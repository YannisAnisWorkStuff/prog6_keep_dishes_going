package com.yiannisftiti.prog6.keepdishesgoing.owner.core.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.RejectOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.LoadIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.SaveIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderRejectedEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RejectOrderUseCaseImpl implements RejectOrderUseCase {

    private final LoadIncomingOrderPort loadIncomingOrderPort;
    private final SaveIncomingOrderPort saveIncomingOrderPort;
    private final PublishDomainEventsPort eventPublisher;

    public RejectOrderUseCaseImpl(LoadIncomingOrderPort loadIncomingOrderPort, SaveIncomingOrderPort saveIncomingOrderPort,
            PublishDomainEventsPort eventPublisher
    ) {
        this.loadIncomingOrderPort = loadIncomingOrderPort;
        this.saveIncomingOrderPort = saveIncomingOrderPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void rejectOrder(UUID restaurantId, UUID orderId, String reason) {
        IncomingOrderProjection order = loadIncomingOrderPort.load(restaurantId, orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.reject();
        saveIncomingOrderPort.save(order);

        eventPublisher.publishEvent(new OrderRejectedEvent(orderId, restaurantId, reason));
    }
}

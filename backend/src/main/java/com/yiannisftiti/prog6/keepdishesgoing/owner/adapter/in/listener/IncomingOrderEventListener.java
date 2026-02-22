package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.listener;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.SaveIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class IncomingOrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(IncomingOrderEventListener.class);

    private final SaveIncomingOrderPort saveIncomingOrderPort;

    public IncomingOrderEventListener(SaveIncomingOrderPort saveIncomingOrderPort) {
        this.saveIncomingOrderPort = saveIncomingOrderPort;
    }

    @EventListener(OrderCreatedEvent.class)
    public void handleOrderCreated(OrderCreatedEvent event) {
        IncomingOrderProjection orderProjection = new IncomingOrderProjection(
                event.orderId(),
                event.restaurantId(),
                event.customerId(),
                event.total()
        );

        saveIncomingOrderPort.save(orderProjection);
        logger.info("Received new order {} for restaurant {}", event.orderId(), event.restaurantId());
    }
}
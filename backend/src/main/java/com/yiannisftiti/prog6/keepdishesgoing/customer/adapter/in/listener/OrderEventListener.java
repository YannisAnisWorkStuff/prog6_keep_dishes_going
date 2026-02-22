package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.listener;

import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.UpdateOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.delivery.DeliveryOrderDeliveredEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.delivery.DeliveryOrderPickedUpEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderAcceptedEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderRejectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    private final UpdateOrderPort updateOrderStatusPort;

    public OrderEventListener(UpdateOrderPort updateOrderStatusPort) {
        this.updateOrderStatusPort = updateOrderStatusPort;
    }

    @EventListener(OrderAcceptedEvent.class)
    @Transactional
    public void handleOrderAccepted(OrderAcceptedEvent event) {
        updateOrderStatusPort.updateStatus(event.orderId(), "ACCEPTED");
        logger.info("Updated order {} to ACCEPTED", event.orderId());
    }

    @EventListener(OrderRejectedEvent.class)
    @Transactional
    public void handleOrderRejected(OrderRejectedEvent event) {
        updateOrderStatusPort.updateStatus(event.orderId(), "REJECTED");
        updateOrderStatusPort.updateRejectionReason(event.orderId(), event.reason());
        logger.info("Updated order {} to REJECTED", event.orderId());
    }


    @EventListener(DeliveryOrderPickedUpEvent.class)
    @Transactional
    public void onPickedUp(DeliveryOrderPickedUpEvent e) {
        updateOrderStatusPort.updateStatus(e.orderId(), "READY_FOR_PICKUP");
        logger.info("Order {} marked READY_FOR_PICKUP in customer context", e.orderId());
    }

    @EventListener(DeliveryOrderDeliveredEvent.class)
    @Transactional
    public void onDelivered(DeliveryOrderDeliveredEvent e) {
        updateOrderStatusPort.updateStatus(e.orderId(), "DELIVERED");
        logger.info("Order {} marked DELIVERED in customer context", e.orderId());
    }

}
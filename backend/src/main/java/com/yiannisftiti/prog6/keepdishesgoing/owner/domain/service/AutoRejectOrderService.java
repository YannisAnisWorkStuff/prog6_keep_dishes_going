package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.service;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.RejectOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.FindPendingOrdersPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutoRejectOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AutoRejectOrderService.class);

    private final FindPendingOrdersPort findPendingOrdersPort;
    private final RejectOrderUseCase rejectOrderUseCase;

    public AutoRejectOrderService(FindPendingOrdersPort findPendingOrdersPort, RejectOrderUseCase rejectOrderUseCase) {
        this.findPendingOrdersPort = findPendingOrdersPort;
        this.rejectOrderUseCase = rejectOrderUseCase;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void autoRejectOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        List<IncomingOrderProjection> pendingOrders = findPendingOrdersPort.findPendingOrdersBefore(cutoff);

        pendingOrders.forEach(order -> {
            rejectOrderUseCase.rejectOrder(
                    order.getRestaurantId(),
                    order.getOrderId(),
                    "Owner did not respond, he's busy/lazy"
            );
            logger.info("Auto rejected order {} for restaurant {}", order.getOrderId(), order.getRestaurantId());
        });
    }
}

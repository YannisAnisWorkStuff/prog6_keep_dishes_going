package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadIncomingOrderPort {
    Optional<IncomingOrderProjection> load(UUID restaurantId, UUID orderId);
    Optional<IncomingOrderProjection> loadByOrderId(UUID orderId);
    List<IncomingOrderProjection> getIncomingOrders();
}
package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.time.LocalDateTime;
import java.util.List;

public interface FindPendingOrdersPort {
    List<IncomingOrderProjection> findPendingOrdersBefore(LocalDateTime timeoff);
    List<IncomingOrderProjection> findAllPendingOrders(RestaurantId restaurantId);
}
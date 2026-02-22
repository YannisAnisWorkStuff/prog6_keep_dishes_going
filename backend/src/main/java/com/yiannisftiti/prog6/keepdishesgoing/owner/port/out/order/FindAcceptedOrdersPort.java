package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.util.List;

public interface FindAcceptedOrdersPort {
    List<IncomingOrderProjection> findAcceptedOrders(RestaurantId restaurantId);
}

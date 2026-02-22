package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(UUID restaurantId, UUID orderId, String reason);
}
package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order;

import java.util.UUID;

public interface ReadyOrderUseCase {
    void markReady(UUID restaurantId, UUID orderId);
}

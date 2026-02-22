package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order;

import java.util.UUID;

public interface AcceptOrderUseCase {
    void acceptOrder(UUID restaurantId, UUID orderId);
}

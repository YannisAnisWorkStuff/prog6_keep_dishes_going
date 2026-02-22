package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface GetOrderStatusUseCase {
    Optional<Order> getOrder(UUID orderId);
}
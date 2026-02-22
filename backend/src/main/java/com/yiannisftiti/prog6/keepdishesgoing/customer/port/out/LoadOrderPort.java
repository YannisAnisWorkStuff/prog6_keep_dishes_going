package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface LoadOrderPort {
    Optional<Order> loadOrder(UUID orderId);
}
package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;

public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCommand command);
    Order createGuestOrder(CreateGuestOrderCommand command);
}
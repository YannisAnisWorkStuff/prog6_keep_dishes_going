package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;

import java.util.Optional;

public interface GetBasketUseCase {
    Optional<Basket> getBasket(CustomerId customerId);
}

package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;

public interface SaveBasketPort {
    void saveBasket(Basket basket);
}
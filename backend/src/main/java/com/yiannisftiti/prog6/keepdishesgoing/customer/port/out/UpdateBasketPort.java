package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import java.util.UUID;

public interface UpdateBasketPort {
    void clearBasket(UUID customerId);
}

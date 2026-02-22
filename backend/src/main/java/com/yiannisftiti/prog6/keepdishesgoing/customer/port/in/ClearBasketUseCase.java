package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import java.util.UUID;

public interface ClearBasketUseCase {
    void clearBasket(UUID customerId);
}
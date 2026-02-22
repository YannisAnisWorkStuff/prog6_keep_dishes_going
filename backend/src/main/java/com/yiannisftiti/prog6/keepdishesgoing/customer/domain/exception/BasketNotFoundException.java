package com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.BasketId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;

import java.util.UUID;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(UUID customerId) {
        super("Basket for customer " + customerId + " is not found");
    }
}

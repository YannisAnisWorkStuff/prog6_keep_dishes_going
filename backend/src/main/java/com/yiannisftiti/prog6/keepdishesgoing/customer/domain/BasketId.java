package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception.BasketNotFoundException;

import java.util.UUID;

public record BasketId(UUID value) {
    public static BasketId of(UUID id) {return new BasketId(id);}
    public static BasketId create() {return new BasketId(UUID.randomUUID());}

    public static BasketNotFoundException basketNotFoundException(UUID customerId) {
        throw new BasketNotFoundException(customerId);
    }
}
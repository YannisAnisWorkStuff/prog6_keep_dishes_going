package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID dishId, String name, BigDecimal price, int quantity) {

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

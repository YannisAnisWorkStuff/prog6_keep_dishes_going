package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.BasketItem;

import java.math.BigDecimal;

public record BasketItemDTO(String dishId, String name, BigDecimal price, int quantity) {

    public static BasketItemDTO fromDomain(BasketItem item) {
        return new BasketItemDTO(item.getDishId().toString(), item.getName(), item.getPrice(), item.getQuantity());
    }
}
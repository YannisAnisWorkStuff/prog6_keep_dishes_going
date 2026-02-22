package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BasketDTO(
        UUID basketId,
        UUID customerId,
        String restaurantId,
        List<BasketItemDTO> items,
        BigDecimal totalPrice
) {
    public static BasketDTO fromDomain(Basket basket) {
        return new BasketDTO(
                basket.getId().value(),
                basket.getCustomerId(),
                basket.getRestaurantId().toString(),
                basket.getItems().stream().map(BasketItemDTO::fromDomain).toList(),
                basket.totalPrice()
        );
    }
}

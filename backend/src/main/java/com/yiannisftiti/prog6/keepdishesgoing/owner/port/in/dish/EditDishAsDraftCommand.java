package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

public record EditDishAsDraftCommand(
        DishId dishId,
        RestaurantId restaurantId,
        String name,
        Dish.Type type,
        String description,
        BigDecimal price,
        String pictureUrl,
        List<String> foodTags
) {
    public Dish toDomain() {
        return new Dish(
                dishId,
                restaurantId,
                name,
                type,
                description,
                price,
                pictureUrl,
                foodTags,
                Dish.State.DRAFT
        );
    }
}

package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.math.BigDecimal;
import java.util.List;

public record DishDTO(
        String id,
        String restaurantId,
        String name,
        String type,
        String description,
        BigDecimal price,
        String pictureUrl,
        List<String> foodTags,
        String state
) {
    public static DishDTO fromDomain(Dish dish) {
        return new DishDTO(
                dish.getId().id().toString(),
                dish.getRestaurantId().id().toString(),
                dish.getName(),
                dish.getType().name(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                dish.getFoodTags(),
                dish.getState().name()
        );
    }
}

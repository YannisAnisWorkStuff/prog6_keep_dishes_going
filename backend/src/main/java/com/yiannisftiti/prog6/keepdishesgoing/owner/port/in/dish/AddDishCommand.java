package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish.Type;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.math.BigDecimal;
import java.util.List;

public record AddDishCommand(
        RestaurantId id,
        String name,
        Type type,
        String description,
        BigDecimal price,
        String pictureUrl,
        List<String> foodTags) {


    public Dish toDomain() {
        return new Dish(id, name, type, description, price, pictureUrl, foodTags);
    }

}
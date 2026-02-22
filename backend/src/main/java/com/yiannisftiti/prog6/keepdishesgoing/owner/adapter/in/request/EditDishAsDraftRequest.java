package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import java.math.BigDecimal;
import java.util.List;

public record EditDishAsDraftRequest(
        String name,
        Dish.Type type,
        String description,
        BigDecimal price,
        String pictureUrl,
        List<String> foodTags
) {}

package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;

public interface DeleteDishPort {
    void deletedDish(DishId dishId);
}

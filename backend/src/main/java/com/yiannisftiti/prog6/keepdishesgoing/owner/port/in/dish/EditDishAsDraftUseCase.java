package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

public interface EditDishAsDraftUseCase {
    void editDishAsDraft(EditDishAsDraftCommand editDishAsDraftCommand) throws RestaurantNotFoundException, DishNotFoundException;
}

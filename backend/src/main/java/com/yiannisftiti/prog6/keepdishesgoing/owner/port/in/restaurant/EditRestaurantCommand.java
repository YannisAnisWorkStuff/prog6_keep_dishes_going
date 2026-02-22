package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DaySchedule;
import java.util.List;

public record EditRestaurantCommand(
        RestaurantId restaurantId,
        String name,
        Address address,
        String cuisineType,
        String email,
        List<String> pictures,
        double preparationTimeMinutes,
        List<DaySchedule> schedules
) {}

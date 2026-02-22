package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DaySchedule;
import java.util.List;

public record EditRestaurantRequest(
        String name,
        Address address,
        String cuisineType,
        String email,
        List<String> pictures,
        double preparationTimeMinutes,
        List<DaySchedule> schedules
) {}

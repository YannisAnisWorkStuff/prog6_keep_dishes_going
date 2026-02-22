package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DaySchedule;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.List;

public record RegisterRestaurantRequest(
        String ownerId,
        String name,
        Address address,
        String cuisineType,
        String email,
        double preparationTimeMinutes,
        List<DaySchedule> schedules,
        List<String> pictures
) {}
package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DaySchedule;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.List;

public record RegisterRestaurantCommand(
        OwnerId ownerId,
        String name,
        Address address,
        String cuisineType,
        String email,
        double preparationTimeMinutes,
        List<DaySchedule> schedules,
        List<String> pictures
) {

    public Restaurant toRestaurant() {
        return new Restaurant(
                RestaurantId.create(),
                ownerId,
                name,
                address,
                cuisineType,
                email,
                pictures,
                preparationTimeMinutes,
                schedules
        );
    }

}

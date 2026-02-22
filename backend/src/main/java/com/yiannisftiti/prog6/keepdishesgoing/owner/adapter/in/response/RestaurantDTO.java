package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DaySchedule;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;

import java.util.List;

public record RestaurantDTO(
        String id,
        String ownerId,
        String name,
        Address address,
        String cuisineType,
        String email,
        double preparationTimeMinutes,
        List<String> pictures,
        List<DaySchedule> schedules
) {
    public static RestaurantDTO fromDomain(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId().id().toString(),
                restaurant.getOwnerId().id().toString(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getEmail(),
                restaurant.getPreparationTimeMinutes(),
                restaurant.getPictures(),
                restaurant.getSchedules()
        );
    }
}

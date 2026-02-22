package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

import java.util.UUID;

public record RestaurantId(UUID id){

    public static RestaurantId of(UUID id){
        return new RestaurantId(id);
    }

    public static RestaurantId create(){
        return new RestaurantId(UUID.randomUUID());
    }

    public static RestaurantNotFoundException notFoundForOwner(OwnerId ownerId){
        return new RestaurantNotFoundException("Restaurant Not found for owner " + ownerId.id().toString());
    }

    public static RestaurantNotFoundException restaurantNotFoundException(RestaurantId id){
        return new RestaurantNotFoundException("Restaurant Not found( " + id.id().toString()+")");
    }

}

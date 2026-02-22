package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

public class PublishedDishLimitExceededException extends RuntimeException{

    public PublishedDishLimitExceededException(RestaurantId restaurantId){
        super("Restaurant with id \"" + restaurantId.id() + "\" has reached it's published dish limit! Cannot add any more dishes!");
    }

}

package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;

import java.util.UUID;

public record DishId(UUID id) {

    public static DishId of(UUID id){
        return new DishId(id);
    }

    public static DishId create(){
        return new DishId(UUID.randomUUID());
    }

    public static DishNotFoundException dishNotFoundException(DishId dish){
        return new DishNotFoundException(dish);
    }

    public static DishNotFoundException dishNotFoundException(String dish){
        return new DishNotFoundException(dish);
    }

}

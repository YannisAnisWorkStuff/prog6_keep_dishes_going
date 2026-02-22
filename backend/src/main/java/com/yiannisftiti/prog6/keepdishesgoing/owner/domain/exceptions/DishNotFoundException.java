package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;

public class DishNotFoundException extends RuntimeException{

    public DishNotFoundException(DishId dish){
        super("Dish with id \"" + dish.id() + "\" not found");
    }

    public DishNotFoundException(String name){
        super("Dish with name \"" + name + "\" is not found");
    }

}

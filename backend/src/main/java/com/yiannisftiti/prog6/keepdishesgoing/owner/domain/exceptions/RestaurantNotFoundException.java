package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions;

public class RestaurantNotFoundException extends Exception {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
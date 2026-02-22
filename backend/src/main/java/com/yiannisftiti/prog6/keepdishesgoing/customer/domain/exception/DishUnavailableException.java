package com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception;

import java.util.UUID;

public class DishUnavailableException extends RuntimeException {

    public DishUnavailableException(UUID dish) {
        super("Dish with id \"" + dish + "\" is not available!");
    }

    public DishUnavailableException(String message) {
        super(message);
    }

}

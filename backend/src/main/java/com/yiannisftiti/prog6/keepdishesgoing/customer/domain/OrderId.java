package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import java.util.UUID;

public record OrderId(UUID orderid) {

    public static OrderId create(){
        return new OrderId(UUID.randomUUID());
    }
    public static OrderId of(UUID id){
        return new OrderId(id);
    }
}

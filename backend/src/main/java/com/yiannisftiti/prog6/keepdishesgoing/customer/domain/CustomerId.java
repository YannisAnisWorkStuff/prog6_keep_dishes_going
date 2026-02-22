package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;
import java.util.UUID;

public record CustomerId(UUID value) {
    public static CustomerId of(UUID id) {return new CustomerId(id);}
    public static CustomerId create() {return new CustomerId(UUID.randomUUID());}
}
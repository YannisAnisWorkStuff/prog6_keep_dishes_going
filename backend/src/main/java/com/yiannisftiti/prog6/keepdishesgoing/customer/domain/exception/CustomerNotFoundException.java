package com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(CustomerId id) {
        super("Customer with id " + id + " not found");
    }
    public CustomerNotFoundException(String email) {
        super("Customer with email " + email + " not found");
    }
}

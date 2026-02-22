package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.UUID;

public record CustomerDTO(UUID id, String name, String email, Address address) {
    public static CustomerDTO fromDomain(Customer c) {
        return new CustomerDTO(c.getId().value(), c.getName(), c.getEmail(),  c.getAddress());
    }
}
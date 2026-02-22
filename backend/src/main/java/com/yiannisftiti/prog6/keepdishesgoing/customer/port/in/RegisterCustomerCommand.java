package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

public record RegisterCustomerCommand(String name, String email, String password, Address address) {}

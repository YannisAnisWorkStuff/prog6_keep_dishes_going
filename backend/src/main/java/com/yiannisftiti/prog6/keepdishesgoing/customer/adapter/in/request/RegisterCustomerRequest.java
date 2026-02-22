package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

public record RegisterCustomerRequest(String name, String email, String password, Address address) {}
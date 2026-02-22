package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;

public interface SaveCustomerPort {
    void saveCustomer(Customer customer);
}
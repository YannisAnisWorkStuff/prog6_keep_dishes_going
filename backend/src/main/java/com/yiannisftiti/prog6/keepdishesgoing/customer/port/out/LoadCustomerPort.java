package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import java.util.Optional;

public interface LoadCustomerPort {
    Optional<Customer> loadCustomerByEmail(String email);
}
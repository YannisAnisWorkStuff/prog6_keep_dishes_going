package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;

public interface RegisterCustomerUseCase {
    Customer registerCustomer(RegisterCustomerCommand command);
}
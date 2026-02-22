package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadCustomerPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveCustomerPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerJPAAdapter implements SaveCustomerPort, LoadCustomerPort {

    private final CustomerJPARepository repository;

    public CustomerJPAAdapter(CustomerJPARepository repository) {
        this.repository = repository;
    }

    private CustomerJPAEntity toCustomerEntity(Customer customer) {
        CustomerJPAEntity entity = new CustomerJPAEntity();
        entity.setId(customer.getId().value());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setEncryptedPassword(customer.getEncryptedPassword());
        entity.setAddress(customer.getAddress());
        return entity;
    }

    private Customer fromDomainEntity(CustomerJPAEntity entity) {
        return new Customer(CustomerId.of(entity.getId()), entity.getName(), entity.getEmail(), entity.getEncryptedPassword(), entity.getAddress());
    }

    @Override
    public void saveCustomer(Customer customer) {
        repository.save(toCustomerEntity(customer));
    }

    @Override
    public Optional<Customer> loadCustomerByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::fromDomainEntity);
    }
}
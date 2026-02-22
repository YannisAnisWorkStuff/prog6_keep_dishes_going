package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.RegisterCustomerCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.RegisterCustomerUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadCustomerPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveCustomerPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.UserRoles;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.CreateKeycloakUserPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.util.PasswordEncryption;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerUseCaseImpl implements RegisterCustomerUseCase {

    private final SaveCustomerPort saveCustomerPort;
    private final LoadCustomerPort loadCustomerPort;
    private final PasswordEncryption passwordEncryption;
    private final CreateKeycloakUserPort keycloakUserCreator;

    public RegisterCustomerUseCaseImpl(SaveCustomerPort saveCustomerPort, LoadCustomerPort loadCustomerPort, PasswordEncryption passwordEncryption
    ,CreateKeycloakUserPort keycloakUserCreator) {
        this.saveCustomerPort = saveCustomerPort;
        this.loadCustomerPort = loadCustomerPort;
        this.passwordEncryption=passwordEncryption;
        this.keycloakUserCreator=keycloakUserCreator;
    }

    @Override
    public Customer registerCustomer(RegisterCustomerCommand command) {
        loadCustomerPort.loadCustomerByEmail(command.email()).ifPresent(c -> {
            throw new IllegalArgumentException("Email already registered");
        });

        String encryptedPassword = passwordEncryption.encryptPassword(command.password());
        Customer customer = new Customer(command.name(), command.email(), encryptedPassword, command.address());
        keycloakUserCreator.createUser(command.email(), command.password(), UserRoles.CUSTOMER);
        saveCustomerPort.saveCustomer(customer);
        return customer;
    }
}
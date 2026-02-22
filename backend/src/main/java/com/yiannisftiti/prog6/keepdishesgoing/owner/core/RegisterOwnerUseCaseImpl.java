package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.RegisterOwnerCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.RegisterOwnerUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.SaveOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.UserRoles;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.CreateKeycloakUserPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterOwnerUseCaseImpl implements RegisterOwnerUseCase {

    private final Logger logger = LoggerFactory.getLogger(RegisterOwnerUseCaseImpl.class);
    private final SaveOwnerPort saveOwnerPort;
    private final CreateKeycloakUserPort keycloakUserCreator;

    public RegisterOwnerUseCaseImpl(SaveOwnerPort saveOwnerPort, CreateKeycloakUserPort keycloakUserCreator) {
        this.saveOwnerPort = saveOwnerPort;
        this.keycloakUserCreator=keycloakUserCreator;
    }

    @Override
    public Owner registerOwner(RegisterOwnerCommand command) {
        Owner owner = command.toDomain();
        logger.info("Registering owner {}", command.name());
        saveOwnerPort.saveOwner(owner);
        keycloakUserCreator.createUser(command.email(), command.password(), UserRoles.OWNER);
        return owner;
    }
}

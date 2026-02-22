package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.OwnerNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.RegisterRestaurantCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.RegisterRestaurantUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.LoadOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.SaveOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterRestaurantUseCaseImpl implements RegisterRestaurantUseCase {

    private final Logger logger = LoggerFactory.getLogger(RegisterRestaurantUseCaseImpl.class);

    private final SaveRestarauntPort saveRestarauntPort;
    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;

    public RegisterRestaurantUseCaseImpl(SaveRestarauntPort saveRestarauntPort, LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort) {
        this.saveRestarauntPort=saveRestarauntPort;
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }

    @Override
    @Transactional
    public Restaurant registerRestaurant(RegisterRestaurantCommand command) throws OwnerNotFoundException {
        OwnerId owner = command.ownerId();
        logger.info("Attempting to register restaurant for owner {}", owner.id());
        Owner o = this.loadOwnerPort.loadBy(owner).orElseThrow( () -> OwnerId.ownerNotFoundException(owner));
        Restaurant r = command.toRestaurant();
        saveRestarauntPort.saveRestarauntPort(r);
        o.registerRestaurant(r.getId());
        saveOwnerPort.saveOwner(o);
        logger.info("Restaurant with id {} has been registered!", r.getId().id());
        return r;
    }
}

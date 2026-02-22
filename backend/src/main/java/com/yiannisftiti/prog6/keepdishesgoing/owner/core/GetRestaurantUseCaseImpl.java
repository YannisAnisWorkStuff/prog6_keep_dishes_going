package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.GetRestaurantUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.LoadIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {

    Logger logger = LoggerFactory.getLogger(GetRestaurantUseCaseImpl.class);

    private final LoadRestarauntPort loadRestarauntPort;
    private final LoadIncomingOrderPort loadIncomingOrderPort;

    public GetRestaurantUseCaseImpl(LoadRestarauntPort loadRestarauntPort, LoadIncomingOrderPort loadIncomingOrderPort) {
        this.loadRestarauntPort = loadRestarauntPort;
        this.loadIncomingOrderPort = loadIncomingOrderPort;
    }

    @Override
    public Restaurant getRestaurantByOwnerId(OwnerId ownerId) throws RestaurantNotFoundException {
        logger.info("Getting Restaraunt for owner {}", ownerId.id());
        return this.loadRestarauntPort.loadByOwner(ownerId).orElseThrow(() -> RestaurantId.notFoundForOwner(ownerId));
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return this.loadRestarauntPort.loadAll();
    }

    @Override
    public Restaurant getRestaurantById(RestaurantId id) throws RestaurantNotFoundException {
        return this.loadRestarauntPort.loadBy(id).orElse(null);
    }

    @Override
    public Double getGuesstimate(RestaurantId id) {
        Restaurant restaurant = loadRestarauntPort.loadBy(id).orElse(null);
        List<IncomingOrderProjection> projections = loadIncomingOrderPort.getIncomingOrders().stream()
                .filter(a -> a.getStatus() == OrderDecisionStatus.ACCEPTED)
                .toList();
        return restaurant==null?null:restaurant.getPreparationTimeMinutes() *
                (projections.isEmpty() ? 1 : projections.size());
    }
}

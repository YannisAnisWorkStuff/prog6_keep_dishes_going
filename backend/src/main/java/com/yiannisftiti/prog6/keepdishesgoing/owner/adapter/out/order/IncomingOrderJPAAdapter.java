package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OrderDecisionStatus;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.FindAcceptedOrdersPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.FindPendingOrdersPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.LoadIncomingOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.SaveIncomingOrderPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class IncomingOrderJPAAdapter implements SaveIncomingOrderPort, LoadIncomingOrderPort, FindPendingOrdersPort,
        FindAcceptedOrdersPort {

    private final IncomingOrderJPARepository repository;

    public IncomingOrderJPAAdapter(IncomingOrderJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(IncomingOrderProjection order) {
        IncomingOrderJPAEntity entity = new IncomingOrderJPAEntity();
        entity.setOrderId(order.getOrderId());
        entity.setRestaurantId(order.getRestaurantId());
        entity.setCustomerId(order.getCustomerId());
        entity.setTotal(order.getTotal());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setStatus(order.getStatus().name());
        repository.save(entity);
    }

    @Override
    public Optional<IncomingOrderProjection> load(UUID restaurantId, UUID orderId) {
        return repository.findByRestaurantIdAndOrderId(restaurantId, orderId)
                .map(this::toDomain);
    }

    @Override
    public Optional<IncomingOrderProjection> loadByOrderId(UUID orderId) {
        return repository.findById(orderId).map(this::toDomain);
    }

    @Override
    public List<IncomingOrderProjection> getIncomingOrders() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private IncomingOrderProjection toDomain(IncomingOrderJPAEntity entity) {
        IncomingOrderProjection projection = new IncomingOrderProjection(
                entity.getOrderId(),
                entity.getRestaurantId(),
                entity.getCustomerId(),
                entity.getTotal()
        );
        if (entity.getStatus().equals("ACCEPTED")) projection.accept();
        else if (entity.getStatus().equals("REJECTED")) projection.reject();
        else projection.pending();
        return projection;
    }

    @Override
    public List<IncomingOrderProjection> findPendingOrdersBefore(LocalDateTime time) {
        return repository.findByStatus("PENDING").stream()
                .filter(entity -> entity.getCreatedAt().isBefore(time))
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomingOrderProjection> findAllPendingOrders(RestaurantId restaurantId) {
        return repository.findByStatus("PENDING").stream()
                .filter(entity -> entity.getRestaurantId().equals(restaurantId.id()))
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomingOrderProjection> findAcceptedOrders(RestaurantId restaurantId) {
        return repository.findByStatus("ACCEPTED").stream()
                .filter(entity -> entity.getRestaurantId().equals(restaurantId.id()))
                .map(this::toDomain)
                .collect(Collectors.toList());    }
}
package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.owner;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.LoadOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.SaveOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.UpdateOwnerPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OwnerJPAAdapter implements LoadOwnerPort, UpdateOwnerPort, SaveOwnerPort {

    private final OwnerJPARepository repository;

    public OwnerJPAAdapter(OwnerJPARepository repository) {
        this.repository = repository;
    }

    private Owner toDomain(OwnerJPAEntity entity) {
        return new Owner(
                new OwnerId(entity.getUuid()),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRestaurantId() != null ? new RestaurantId(entity.getRestaurantId()) : null
        );
    }

    private OwnerJPAEntity toEntity(Owner owner) {
        return new OwnerJPAEntity(
                owner.getId().id(),
                owner.getName(),
                owner.getEmail(),
                owner.getPasswordHash(),
                owner.getRestaurant() != null ? owner.getRestaurant().id() : null
        );
    }

    @Override
    public Optional<Owner> loadBy(OwnerId ownerId) {
        return repository.findById(ownerId.id()).map(this::toDomain);
    }

    @Override
    public Optional<Owner> loadByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Owner> loadAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Owner updateOwner(Owner owner) {
        OwnerJPAEntity entity = repository.save(toEntity(owner));
        return toDomain(entity);
    }

    @Override
    public void saveOwner(Owner owner) {
        repository.save(toEntity(owner));
    }
}

package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.OwnerNotFoundException;

import java.util.UUID;

public record OwnerId(UUID id) {

    public static OwnerId of(UUID id) {return new OwnerId(id);}
    public static OwnerId create() {return new OwnerId(UUID.randomUUID());}

    public static OwnerNotFoundException ownerNotFoundException(OwnerId ownerId) {
        return new OwnerNotFoundException(ownerId);
    }
    public static OwnerNotFoundException ownerNotFoundException(String email) {
        return new OwnerNotFoundException(email);
    }

}

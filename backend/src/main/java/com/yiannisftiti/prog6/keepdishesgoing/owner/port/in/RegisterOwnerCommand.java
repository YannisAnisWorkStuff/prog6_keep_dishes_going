package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;

public record RegisterOwnerCommand(
        String name,
        String email,
        String password,
        String passwordHash
) {
    public Owner toDomain() {
        return new Owner(OwnerId.create(), name, email, passwordHash, null);
    }
}

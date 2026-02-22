package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;

import java.util.List;
import java.util.Optional;

public interface LoadOwnerPort {

    Optional<Owner> loadBy(OwnerId owner);

    Optional<Owner> loadByEmail(String email);

    List<Owner> loadAll();
}

package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;

public class OwnerNotFoundException extends  RuntimeException{

    public OwnerNotFoundException(OwnerId  ownerId) {
        super("Owner with id \"" + ownerId.id() + "\" does not exist!");
    }

    public OwnerNotFoundException(String email) {
        super("Owner with email \"" + email + "\" does not exist!");
    }
}

package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;

public interface RegisterOwnerUseCase {

    Owner registerOwner(RegisterOwnerCommand command);

}

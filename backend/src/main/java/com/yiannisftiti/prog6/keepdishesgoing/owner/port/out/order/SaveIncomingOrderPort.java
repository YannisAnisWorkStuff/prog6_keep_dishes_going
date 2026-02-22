package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;

public interface SaveIncomingOrderPort {
    void save(IncomingOrderProjection order);
}

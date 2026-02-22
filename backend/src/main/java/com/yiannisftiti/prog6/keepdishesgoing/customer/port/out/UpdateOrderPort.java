package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import java.util.UUID;

public interface UpdateOrderPort {
    void updateStatus(UUID orderId, String status);
    void updateRejectionReason(UUID orderId, String reason);
}

package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request;

import java.util.UUID;

public record UpdateBasketQuantityRequest(UUID dishId, int quantity) {}
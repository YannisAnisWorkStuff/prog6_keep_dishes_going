package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request;

public record RegisterOwnerRequest(
        String name,
        String email,
        String password
) {}
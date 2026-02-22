package com.yiannisftiti.prog6.keepdishesgoing.shared.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.UserRoles;

public interface CreateKeycloakUserPort {
    void createUser(String email, String password, UserRoles role);
}
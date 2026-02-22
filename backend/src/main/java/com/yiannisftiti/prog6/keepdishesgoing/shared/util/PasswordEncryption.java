package com.yiannisftiti.prog6.keepdishesgoing.shared.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {

    public String encryptPassword(String password) {
        return "encryption{"+password+"}";//random for now
    }

}

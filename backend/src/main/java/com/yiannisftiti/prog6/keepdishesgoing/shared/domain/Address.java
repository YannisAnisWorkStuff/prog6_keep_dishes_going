package com.yiannisftiti.prog6.keepdishesgoing.shared.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
        String street,
        String number,
        String postalCode,
        String city,
        String country) {
}
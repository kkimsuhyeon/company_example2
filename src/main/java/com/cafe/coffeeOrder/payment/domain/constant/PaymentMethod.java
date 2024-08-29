package com.cafe.coffeeOrder.payment.domain.constant;

import lombok.Getter;

public enum PaymentMethod {
    CASH("현금"),
    CARD("카드"),
    ;

    @Getter
    private String name;

    private PaymentMethod(String name) {
        this.name = name;
    }
}
package com.cafe.coffeeOrder.payment.domain.constant;

import lombok.Getter;

public enum PaymentStatus {
    WAIT("결제 대기"),
    SUCCESS("결제 성공"),
    FAIL("결제 실패");

    @Getter
    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }
}
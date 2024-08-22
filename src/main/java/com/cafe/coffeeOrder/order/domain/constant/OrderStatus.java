package com.cafe.coffeeOrder.order.domain.constant;

import lombok.Getter;

public enum OrderStatus {

    WAIT("주문 대기"),
    SUCCESS("주문 완료"),
    DELIVERY_COMPLETE("배송 완료");

    @Getter
    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

}
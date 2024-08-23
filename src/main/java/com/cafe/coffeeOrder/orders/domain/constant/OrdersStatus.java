package com.cafe.coffeeOrder.orders.domain.constant;

import lombok.Getter;

public enum OrdersStatus {

    WAIT("주문 대기"),
    CANCEL("주문 취소"),
    SUCCESS("주문 완료"),
    DELIVERY_COMPLETE("배송 완료");

    @Getter
    private String description;

    OrdersStatus(String description) {
        this.description = description;
    }

}
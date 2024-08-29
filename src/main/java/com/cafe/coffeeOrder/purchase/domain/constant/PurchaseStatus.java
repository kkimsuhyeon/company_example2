package com.cafe.coffeeOrder.purchase.domain.constant;

import lombok.Getter;

public enum PurchaseStatus {

    WAIT("주문 대기"),
    CANCEL("주문 취소"),
    SUCCESS("주문 완료"),
    DELIVERY_COMPLETE("배송 완료");

    @Getter
    private final String description;

    PurchaseStatus(String description) {
        this.description = description;
    }

    // 컨버터 생성하면 내부 값을 사용할 수 있음
    // @Convert로 Column에 사용

}
package com.cafe.coffeeOrder.receipt.domain.constant;

public enum ReceiptStatus {

    WAIT("대기"),
    SUCCESS("결제 완료"),
    FINISH("전체 완료");

    private String description;

    ReceiptStatus(String description) {
        this.description = description;
    }
}

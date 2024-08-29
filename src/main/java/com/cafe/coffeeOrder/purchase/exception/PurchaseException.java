package com.cafe.coffeeOrder.purchase.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PurchaseException implements ExceptionInfo {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "ORD-001", "해당 주문 존재하지 않음"),
    BAD_STATUS(HttpStatus.BAD_REQUEST, "ORD-002", "정상적인 상태가 필요함");

    private HttpStatus status;
    private String code;
    private String message;

    private PurchaseException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
package com.cafe.coffeeOrder.orders.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OrdersException implements ExceptionInfo {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "ORD-001", "해당 주문 존재하지 않음");

    private HttpStatus status;
    private String code;
    private String message;

    private OrdersException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
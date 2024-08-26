package com.cafe.coffeeOrder.customer.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomerException implements ExceptionInfo {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "CUS-001", "해당 유저 존재하지 않음");

    private HttpStatus status;
    private String code;
    private String message;

    private CustomerException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
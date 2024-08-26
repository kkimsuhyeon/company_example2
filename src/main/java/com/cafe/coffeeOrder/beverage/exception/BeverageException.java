package com.cafe.coffeeOrder.beverage.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BeverageException implements ExceptionInfo {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "BEVERAGE-001", "해당 음료 존재하지 않음");

    private HttpStatus status;
    private String code;
    private String message;

    BeverageException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
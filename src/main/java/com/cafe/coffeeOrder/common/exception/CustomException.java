package com.cafe.coffeeOrder.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public CustomException(HttpStatus status, String errorCode, String message) {
        super(message);

        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomException(ExceptionInfo info) {
        super(info.getMessage());

        this.status = info.getStatus();
        this.errorCode = info.getCode();
        this.message = info.getMessage();
    }
}
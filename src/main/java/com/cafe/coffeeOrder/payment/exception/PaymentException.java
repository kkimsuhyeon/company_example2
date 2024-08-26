package com.cafe.coffeeOrder.payment.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentException implements ExceptionInfo {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "PAY-001", "해당 결제 내역 존재하지 않음"),
    BAD_STATUS(HttpStatus.BAD_REQUEST, "PAY-002", "설정할 수 없는 상태값");

    private HttpStatus status;
    private String code;
    private String message;

    private PaymentException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
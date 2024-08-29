package com.cafe.coffeeOrder.payment.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentException implements ExceptionInfo {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "PAY-001", "해당 결제 내역 존재하지 않음"),
    FAIL_PAY(HttpStatus.INTERNAL_SERVER_ERROR, "PAY-002", "결제 실패");

    private HttpStatus status;
    private String code;
    private String message;

    private PaymentException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
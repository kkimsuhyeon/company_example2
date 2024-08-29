package com.cafe.coffeeOrder.receipt.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReceiptException implements ExceptionInfo {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "RCP-001", "해당 영수증 존재하지 않음"),
    BAD_STATUS(HttpStatus.BAD_REQUEST, "RCP-002", "정상적인 상태가 필요합니다");

    private HttpStatus status;
    private String code;
    private String message;

    private ReceiptException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
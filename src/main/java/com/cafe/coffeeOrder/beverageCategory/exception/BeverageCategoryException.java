package com.cafe.coffeeOrder.beverageCategory.exception;

import com.cafe.coffeeOrder.common.exception.ExceptionInfo;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BeverageCategoryException implements ExceptionInfo {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY-001", "해당 카테고리 존재하지 않음");

    private HttpStatus status;
    private String code;
    private String message;

    BeverageCategoryException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
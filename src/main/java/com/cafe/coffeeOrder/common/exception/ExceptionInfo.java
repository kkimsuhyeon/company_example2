package com.cafe.coffeeOrder.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionInfo {

    public HttpStatus getStatus();

    public String getCode();

    public String getMessage();
}
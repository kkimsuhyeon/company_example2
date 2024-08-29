package com.cafe.coffeeOrder.common.exception;

import com.cafe.coffeeOrder.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex, WebRequest request) {
        return ApiResponse.fail(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, ex.getBindingResult().getAllErrors().get(0).getCode(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

//    @ExceptionHandler(ServerErrorException.class)
//    public ResponseEntity<ApiResponse<Void>> handleServerException(ServerErrorException ex, WebRequest request) {
//        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleUnknownException(Exception ex, WebRequest request) {
//        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage());
//    }


}
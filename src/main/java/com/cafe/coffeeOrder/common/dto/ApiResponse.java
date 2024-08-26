package com.cafe.coffeeOrder.common.dto;

import com.cafe.coffeeOrder.common.exception.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private T data;
    private String code;
    private String message;

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, T data) {
        ApiResponse<T> response = new ApiResponse<>(data, null, null);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ApiResponse<Void>> fail(HttpStatus status, String code, String message) {
        ApiResponse<Void> response = new ApiResponse<>(null, code, message);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ApiResponse<Void>> fail(CustomException ex) {
        ApiResponse<Void> response = new ApiResponse<>(null, ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }

}
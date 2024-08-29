package com.cafe.coffeeOrder.payment.dto;

import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponsePayment {

    private long id;

    private int price;

    private PaymentMethod method;

    private PaymentStatus status;

    public static ResponsePayment from(Payment entity) {
        return new ResponsePayment(entity.getId(), entity.getPrice(), entity.getMethod(), entity.getStatus());
    }
}
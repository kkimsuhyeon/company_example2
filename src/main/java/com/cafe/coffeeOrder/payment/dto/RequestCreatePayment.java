package com.cafe.coffeeOrder.payment.dto;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestCreatePayment {

    private long orderId;

    private PaymentStatus status;
}

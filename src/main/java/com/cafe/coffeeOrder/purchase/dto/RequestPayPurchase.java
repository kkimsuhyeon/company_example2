package com.cafe.coffeeOrder.purchase.dto;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestPayPurchase {

    private Long purchaseId;

    private int price;

    private PaymentMethod method;

    public static RequestPayPurchase of(Long purchaseId, int price, PaymentMethod method) {
        return new RequestPayPurchase(purchaseId, price, method);
    }
}
package com.cafe.coffeeOrder.payment.dto;

import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestRegistPayment {

    @NotNull
    private long purchaseId;

    @NotNull
    private Integer price;

    @NotNull
    private PaymentMethod method;

    @NotNull
    private PaymentStatus status;

    public static RequestRegistPayment of(long purchaseId, Integer price, PaymentMethod method, PaymentStatus status) {
        return new RequestRegistPayment(purchaseId, price, method, status);
    }

    public Payment toEntity(Purchase purchase) {
        return Payment.of(purchase, this.price, this.method, this.status);
    }

}

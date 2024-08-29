package com.cafe.coffeeOrder.receipt.dto;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestPayReceipt {

    @NotNull
    private Long receiptId;

    @NotEmpty
    private List<Long> purchaseIds;

    @NotNull
    private int price;

    @NotNull
    private PaymentMethod method;

    public static RequestPayReceipt of(Long receiptId, List<Long> purchaseIds, int price, PaymentMethod method) {
        return new RequestPayReceipt(receiptId, purchaseIds, price, method);
    }

}

package com.cafe.coffeeOrder.purchase.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreatePurchase {

    @NotNull
    private Long receiptId;

    @NotEmpty
    private Long beverageId;

    public static RequestCreatePurchase of(long receiptId, Long beverageId) {
        return new RequestCreatePurchase(receiptId, beverageId);
    }

}
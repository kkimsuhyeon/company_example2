package com.cafe.coffeeOrder.receipt.dto;

import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseReceipt {

    private long id;

    private ReceiptStatus status;

    public static ResponseReceipt fromEntity(Receipt entity) {
        return new ResponseReceipt(entity.getId(), entity.getStatus());
    }
}

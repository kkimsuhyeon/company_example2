package com.cafe.coffeeOrder.receipt.dto;

import com.cafe.coffeeOrder.customer.dto.ResponseCustomer;
import com.cafe.coffeeOrder.purchase.dto.ResponsePurchaseWithoutReceipt;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseReceiptItem {

    private long id;

    private ReceiptStatus status;

    private int totalPrice;

    private ResponseCustomer customer;

    private List<ResponsePurchaseWithoutReceipt> purchases;

    public static ResponseReceiptItem fromEntity(Receipt entity) {
        return new ResponseReceiptItem(
                entity.getId(),
                entity.getStatus(),
                entity.getTotalPrice(),
                ResponseCustomer.from(entity.getCustomer()),
                entity.getPurchases().stream().map(ResponsePurchaseWithoutReceipt::fromEntity).toList()
        );
    }
}

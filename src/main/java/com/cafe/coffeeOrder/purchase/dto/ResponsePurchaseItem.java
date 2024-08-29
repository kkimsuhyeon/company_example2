package com.cafe.coffeeOrder.purchase.dto;

import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.payment.dto.ResponsePayment;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceipt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponsePurchaseItem {

    private Long id;

    private PurchaseStatus status;

    private ResponseBeverageItem beverage;

    private ResponseReceipt receipt;

    private List<ResponsePayment> payments;

    public static ResponsePurchaseItem fromEntity(Purchase entity) {
        return new ResponsePurchaseItem(
                entity.getId(),
                entity.getStatus(),
                ResponseBeverageItem.from(entity.getBeverage()),
                ResponseReceipt.fromEntity(entity.getReceipt()),
                Optional.ofNullable(entity.getPayments())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(ResponsePayment::from)
                        .collect(Collectors.toList()));
    }
}
package com.cafe.coffeeOrder.purchase.dto;

import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.payment.dto.ResponsePayment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponsePurchaseWithoutReceipt {

    private Long id;

    private PurchaseStatus status;

    private int price;

    private ResponseBeverageItem beverage;

    private List<ResponsePayment> payments;

    public static ResponsePurchaseWithoutReceipt fromEntity(Purchase entity) {
        if (entity.getPayments() == null) {
            return new ResponsePurchaseWithoutReceipt(
                    entity.getId(),
                    entity.getStatus(),
                    entity.getPrice(),
                    ResponseBeverageItem.from(entity.getBeverage()),
                    null);
        } else {
            return new ResponsePurchaseWithoutReceipt(
                    entity.getId(),
                    entity.getStatus(),
                    entity.getPrice(),
                    ResponseBeverageItem.from(entity.getBeverage()),
                    entity.getPayments().stream().map(ResponsePayment::from).collect(Collectors.toList()));
        }
    }


}

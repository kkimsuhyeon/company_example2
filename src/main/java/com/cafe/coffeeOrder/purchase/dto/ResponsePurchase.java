package com.cafe.coffeeOrder.purchase.dto;


import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponsePurchase {

    private long id;

    private PurchaseStatus status;

    public static ResponsePurchase fromEntity(Purchase entity) {
        return new ResponsePurchase(entity.getId(), entity.getStatus());
    }

}

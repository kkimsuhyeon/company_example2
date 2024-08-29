package com.cafe.coffeeOrder.purchase.service;

import com.cafe.coffeeOrder.purchase.dto.RequestCreatePurchase;
import com.cafe.coffeeOrder.purchase.dto.RequestPayPurchase;
import com.cafe.coffeeOrder.purchase.dto.ResponsePurchaseItem;

public interface PurchaseService {

    public ResponsePurchaseItem getPurchase(long id);

    public ResponsePurchaseItem createPurchase(RequestCreatePurchase request);

    public ResponsePurchaseItem payPurchase(RequestPayPurchase request);

    public ResponsePurchaseItem sendBeverage(long id);

}
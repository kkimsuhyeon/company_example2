package com.cafe.coffeeOrder.receipt.service;

import com.cafe.coffeeOrder.receipt.dto.RequestCreateReceipt;
import com.cafe.coffeeOrder.receipt.dto.RequestPayReceipt;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceipt;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceiptItem;

public interface ReceiptService {

    public ResponseReceiptItem getReceipt(long id);

    public ResponseReceiptItem createReceipt(RequestCreateReceipt request);

    public ResponseReceiptItem payReceipt(RequestPayReceipt request);
}

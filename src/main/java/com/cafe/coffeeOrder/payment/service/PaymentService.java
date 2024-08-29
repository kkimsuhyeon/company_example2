package com.cafe.coffeeOrder.payment.service;

import com.cafe.coffeeOrder.payment.dto.RequestRegistPayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithPurchase;

import java.util.List;

public interface PaymentService {

    ResponsePaymentWithPurchase getPayment(long id);

    ResponsePaymentWithPurchase registPayment(RequestRegistPayment request);

    List<ResponsePaymentWithPurchase> registMultiPayment(List<RequestRegistPayment> requests);

}
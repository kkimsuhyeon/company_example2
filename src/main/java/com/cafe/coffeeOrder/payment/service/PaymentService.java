package com.cafe.coffeeOrder.payment.service;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestCreatePayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithOrder;

public interface PaymentService {

    public ResponsePaymentWithOrder getPayment(long id);

    public ResponsePaymentWithOrder createPayment(RequestCreatePayment request);

    public ResponsePaymentWithOrder successPayment(long orderId);

    public ResponsePaymentWithOrder failPayment(long orderId);

}
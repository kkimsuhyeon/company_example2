package com.cafe.coffeeOrder.payment.service;

import com.cafe.coffeeOrder.common.exception.CustomException;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.exception.PurchaseException;
import com.cafe.coffeeOrder.purchase.repository.PurchaseRepository;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestRegistPayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithPurchase;
import com.cafe.coffeeOrder.payment.exception.PaymentException;
import com.cafe.coffeeOrder.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PurchaseRepository purchaseRepository) {
        this.paymentRepository = paymentRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public ResponsePaymentWithPurchase getPayment(long id) {
        return paymentRepository.selectPaymentById(id)
                .map(ResponsePaymentWithPurchase::fromEntity)
                .orElseThrow(() -> new CustomException(PaymentException.NOT_FOUND));
    }

    @Override
    public ResponsePaymentWithPurchase registPayment(RequestRegistPayment request) {

        Purchase purchase = purchaseRepository.selectPurchaseById(request.getPurchaseId())
                .orElseThrow(() -> new CustomException(PurchaseException.NOT_FOUND));

        Payment payment = paymentRepository.insertPayment(request.toEntity(purchase));

        return ResponsePaymentWithPurchase.fromEntity(payment);
    }

    @Override
    public List<ResponsePaymentWithPurchase> registMultiPayment(List<RequestRegistPayment> requests) {
        return requests.stream().map(this::registPayment).toList();
    }
}

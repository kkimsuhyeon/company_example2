package com.cafe.coffeeOrder.payment.repository;

import com.cafe.coffeeOrder.payment.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {
    public Optional<Payment> selectPaymentById(long id);

    public Payment insertPayment(Payment payment);
}
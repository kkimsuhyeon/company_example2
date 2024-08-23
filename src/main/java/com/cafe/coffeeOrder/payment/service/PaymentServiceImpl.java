package com.cafe.coffeeOrder.payment.service;

import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.repository.OrdersRepository;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestCreatePayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithOrder;
import com.cafe.coffeeOrder.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrdersRepository ordersRepository) {
        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public ResponsePaymentWithOrder getPayment(long id) {
        return paymentRepository.selectPaymentById(id)
                .map(ResponsePaymentWithOrder::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 결제내역 없음"));
    }

    @Override
    @Transactional
    public ResponsePaymentWithOrder createPayment(RequestCreatePayment request) {
        switch (request.getStatus()) {
            case SUCCESS -> {
                return successPayment(request.getOrderId());
            }

            case FAIL -> {
                return failPayment(request.getOrderId());
            }
            default -> {
                throw new IllegalArgumentException("값 잘못 넘어옴");
            }
        }
    }

    @Override
    @Transactional
    public ResponsePaymentWithOrder successPayment(long orderId) {
        Orders orders = ordersRepository.selectOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문은 존재하지 않음"));
        Payment payment = Payment.of(orders, PaymentStatus.SUCCESS);

        paymentRepository.insertPayment(payment);

        return ResponsePaymentWithOrder.fromEntity(payment);
    }

    @Override
    @Transactional
    public ResponsePaymentWithOrder failPayment(long orderId) {
        Orders orders = ordersRepository.selectOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문은 존재하지 않음"));
        Payment payment = Payment.of(orders, PaymentStatus.FAIL);

        paymentRepository.insertPayment(payment);

        return ResponsePaymentWithOrder.fromEntity(payment);
    }
}
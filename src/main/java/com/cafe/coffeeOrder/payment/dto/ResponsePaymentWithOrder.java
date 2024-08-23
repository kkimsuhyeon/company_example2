package com.cafe.coffeeOrder.payment.dto;


import com.cafe.coffeeOrder.orders.dto.ResponseOrders;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponsePaymentWithOrder {

    private long id;

    private PaymentStatus status;

    private ResponseOrders orders;

    public static ResponsePaymentWithOrder fromEntity(Payment entity) {
        return new ResponsePaymentWithOrder(
                entity.getId(),
                entity.getStatus(),
                ResponseOrders.fromEntity(entity.getOrders()));
    }

}

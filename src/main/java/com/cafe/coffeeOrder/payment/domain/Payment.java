package com.cafe.coffeeOrder.payment.domain;

import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.AuditingFields;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(callSuper = true, exclude = "orders")
public class Payment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false, name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    private Payment(Orders orders, PaymentStatus status) {
        this.orders = orders;
        this.status = status;
        orders.setPayment(this);
    }

    public static Payment of(Orders orders, PaymentStatus status) {
        return new Payment(orders, status);
    }


}
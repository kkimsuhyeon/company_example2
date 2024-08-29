package com.cafe.coffeeOrder.payment.domain;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.AuditingFields;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(callSuper = true, exclude = "purchase")
public class Payment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false, name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Column(nullable = false)
    private int price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private Payment(Purchase purchase, int price, PaymentMethod method, PaymentStatus status) {
        this.purchase = purchase;
        this.method = method;
        this.price = price;
        setStatus(status);
        purchase.addPayment(this);
    }

    private void setStatus(PaymentStatus status) {
        this.status = status;
        if (status.equals(PaymentStatus.SUCCESS)) {
            this.purchase.success();
        }
    }

    public static Payment of(Purchase purchase, int price, PaymentMethod method, PaymentStatus status) {
        return new Payment(purchase, price, method, status);
    }
}
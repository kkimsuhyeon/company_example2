package com.cafe.coffeeOrder.purchase.domain;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.purchase.domain.constant.AuditingFields;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(callSuper = true, exclude = {"receipt"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Purchase extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, name = "purchase_status")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Column(nullable = false)
    private int price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    private Purchase(Receipt receipt, Beverage beverage) {
        this.beverage = beverage;
        this.price = beverage.getPrice();
        this.status = PurchaseStatus.WAIT;
        setReceipt(receipt);
    }

    public static Purchase of(Receipt receipt, Beverage beverage) {
        return new Purchase(receipt, beverage);
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
        if (!receipt.getPurchases().contains(this)) {
            receipt.addPurchase(this);
        }
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        success();
    }

    public void cancel() {
        if (this.status.equals(PurchaseStatus.WAIT)) {
            this.status = PurchaseStatus.CANCEL;
        }
    }

    public void success() {
        if (this.status.equals(PurchaseStatus.WAIT) && payments.stream().anyMatch((item) -> item.getStatus().equals(PaymentStatus.SUCCESS))) {
            this.status = PurchaseStatus.SUCCESS;
            this.receipt.success();
        }
    }

    public void complete() {
        if (this.status.equals(PurchaseStatus.SUCCESS)) {
            this.status = PurchaseStatus.DELIVERY_COMPLETE;
            this.receipt.finish();
        }
    }
}
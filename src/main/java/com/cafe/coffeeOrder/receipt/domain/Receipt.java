package com.cafe.coffeeOrder.receipt.domain;

import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.receipt.domain.constant.AuditingFields;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Receipt extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "receipt_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;

    @Transient
    private int totalPrice;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    protected Receipt(Customer customer) {
        this.status = ReceiptStatus.WAIT;
        this.customer = customer;
    }

    public static Receipt of(Customer customer) {
        return new Receipt(customer);
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
        this.totalPrice = this.totalPrice + purchase.getPrice();
        if (purchase.getReceipt() != this) {
            purchase.setReceipt(this);
        }
    }

    public void success() {
        if (this.status == ReceiptStatus.WAIT && purchases.stream().allMatch(item -> item.getStatus().equals(PurchaseStatus.SUCCESS))) {
            this.status = ReceiptStatus.SUCCESS;
        }
    }

    public void finish() {
        if (this.status == ReceiptStatus.SUCCESS && purchases.stream().allMatch(item -> item.getStatus().equals(PurchaseStatus.DELIVERY_COMPLETE))) {
            this.status = ReceiptStatus.FINISH;
        }
    }
}
package com.cafe.coffeeOrder.orders.domain;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.orders.domain.constant.AuditingFields;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Orders extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Column(nullable = false, name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrdersStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

    private Orders(Customer customer, Beverage beverage) {
        this.customer = customer;
        this.beverage = beverage;
        setStatus(OrdersStatus.WAIT);
    }

    public static Orders of(Customer customer, Beverage beverage) {
        return new Orders(customer, beverage);
    }

    public void setPayment(Payment payment) {
        payments.add(payment);
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            setStatus(OrdersStatus.SUCCESS);
        }
    }

    public void setStatus(OrdersStatus status) {

        String errorMessage = "정상적인 상태가 필요함";

        switch (status) {
            case WAIT: {
                if (this.status == null) {
                    this.status = status;
                    break;
                }
                throw new IllegalArgumentException(errorMessage);

            }

            case SUCCESS, CANCEL: {
                if (this.status.equals(OrdersStatus.WAIT)) {
                    this.status = status;
                    break;
                }
                throw new IllegalArgumentException(errorMessage);

            }

            case DELIVERY_COMPLETE: {
                if (this.status.equals(OrdersStatus.SUCCESS)) {
                    this.status = status;
                    break;
                }
                throw new IllegalArgumentException(errorMessage);
            }

            default: {
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

}
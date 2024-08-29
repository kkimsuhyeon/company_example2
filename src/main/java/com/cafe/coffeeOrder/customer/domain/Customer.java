package com.cafe.coffeeOrder.customer.domain;

import com.cafe.coffeeOrder.receipt.domain.Receipt;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString(exclude = {"receipts"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Receipt> receipts = new ArrayList<>();

    private Customer(String name) {
        this.name = name;
    }

    public static Customer of(String name) {
        return new Customer(name);
    }

}
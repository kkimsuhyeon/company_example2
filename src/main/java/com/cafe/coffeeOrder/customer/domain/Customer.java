package com.cafe.coffeeOrder.customer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Customer(String name) {
        this.name = name;
    }

    public static Customer of(String name) {
        return new Customer(name);
    }

}
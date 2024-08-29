package com.cafe.coffeeOrder.beverage.domain;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beverage_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    // 금액 추가
    @Column(nullable = false)
    private Integer price;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_category_id")
    private BeverageCategory category;

    private Beverage(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    private Beverage(String name, Integer price, BeverageCategory category) {
        this(name, price);
        setCategory(category);
    }

    public static Beverage of(String name, Integer price) {
        return new Beverage(name, price);
    }

    public static Beverage of(String name, Integer price, BeverageCategory category) {
        return new Beverage(name, price, category);
    }

    public void setCategory(BeverageCategory category) {
        this.category = category;
        if (!category.getBeverages().contains(this)) {
            category.addBeverage(this);
        }
    }
}

package com.cafe.coffeeOrder.beverage.domain;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
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

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_category_id")
    private BeverageCategory category;

    private Beverage(String name) {
        this.name = name;
    }

    public static Beverage of(String name) {
        return new Beverage(name);
    }

    public void setCategory(BeverageCategory category) {
        this.category = category;
        if (!category.getBeverages().contains(this)) {
            category.addBeverage(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beverage beverage)) return false;
        return Objects.equals(id, beverage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
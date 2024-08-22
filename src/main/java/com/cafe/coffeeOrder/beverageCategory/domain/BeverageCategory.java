package com.cafe.coffeeOrder.beverageCategory.domain;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class BeverageCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beverage_category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Beverage> beverages = new HashSet<>();

    private BeverageCategory(String name) {
        this.name = name;
    }

    public static BeverageCategory of(String name) {
        return new BeverageCategory(name);
    }

    public void addBeverage(Beverage beverage) {
        this.beverages.add(beverage);
        if (beverage.getCategory() == null || !beverage.getCategory().equals(this)) {
            beverage.setCategory(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeverageCategory that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
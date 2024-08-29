package com.cafe.coffeeOrder.beverage.dto;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateBeverage {

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    private long categoryId;

    public Beverage toEntity() {
        return Beverage.of(name, price);
    }

    private RequestCreateBeverage(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.categoryId = builder.categoryId;
    }

    public static class Builder {
        private final String name;
        private final Integer price;
        private long categoryId;


        public Builder(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public Builder categoryId(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public RequestCreateBeverage build() {
            return new RequestCreateBeverage(this);
        }
    }


}
package com.cafe.coffeeOrder.beverage.dto;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import lombok.Builder;

@Builder
public class RequestCreateBeverage {

    private String name;

    public Beverage toEntity() {
        return Beverage.of(name);
    }

}
package com.cafe.coffeeOrder.beverage.dto;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestCreateBeverage {

    @NotNull
    private String name;

    private long categoryId;

    public Beverage toEntity() {
        return Beverage.of(name);
    }

}
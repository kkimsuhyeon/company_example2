package com.cafe.coffeeOrder.beverageCategory.dto;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestCreateBeverageCategory {
    private String name;

    public BeverageCategory toEntity() {
        return BeverageCategory.of(name);
    }
}

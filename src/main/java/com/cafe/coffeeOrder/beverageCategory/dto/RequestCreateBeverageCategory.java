package com.cafe.coffeeOrder.beverageCategory.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestCreateBeverageCategory {
    private String name;
}

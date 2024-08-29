package com.cafe.coffeeOrder.beverageCategory.dto;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseBeverageCategory {
    private Long id;
    private String name;

    public static ResponseBeverageCategory from(BeverageCategory entity) {
        return new ResponseBeverageCategory(entity.getId(), entity.getName());
    }
}

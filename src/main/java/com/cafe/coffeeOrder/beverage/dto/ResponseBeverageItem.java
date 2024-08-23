package com.cafe.coffeeOrder.beverage.dto;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverageCategory.dto.ResponseBeverageCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseBeverageItem {
    private long id;
    private String name;
    private ResponseBeverageCategory category;

    public static ResponseBeverageItem from(Beverage entity) {
        if (entity.getCategory() == null) {
            return new ResponseBeverageItem(entity.getId(), entity.getName(), null);
        } else {
            return new ResponseBeverageItem(
                    entity.getId(),
                    entity.getName(),
                    ResponseBeverageCategory.from(entity.getCategory()));
        }
    }
}
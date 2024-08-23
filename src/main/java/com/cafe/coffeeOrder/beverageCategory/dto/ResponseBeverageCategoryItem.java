package com.cafe.coffeeOrder.beverageCategory.dto;

import com.cafe.coffeeOrder.beverage.dto.ResponseBeverage;
import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseBeverageCategoryItem {

    private long id;

    private String name;

    private Set<ResponseBeverage> beverages;

    public static ResponseBeverageCategoryItem fromEntity(BeverageCategory entity) {
        return new ResponseBeverageCategoryItem(
                entity.getId(),
                entity.getName(),
                entity.getBeverages().stream().map(ResponseBeverage::fromEntity).collect(Collectors.toSet()));
    }


}

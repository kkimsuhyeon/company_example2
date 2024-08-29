package com.cafe.coffeeOrder.beverage.dto;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseBeverage {
    private long id;
    private String name;
    private int price;

    public static ResponseBeverage fromEntity(Beverage entity) {
        return new ResponseBeverage(entity.getId(), entity.getName(), entity.getPrice());
    }
}

package com.cafe.coffeeOrder.beverage;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.service.BeverageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeverageTest {

    @Autowired
    BeverageService beverageService;

    @Test
    @DisplayName("음료 생성")
    void create_beverage() {
        RequestCreateBeverage request = RequestCreateBeverage.builder().name("test").build();

        beverageService.createBeverage(request);
    }
}

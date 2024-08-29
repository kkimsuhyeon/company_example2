package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;

import java.util.List;

public interface BeverageService {

    public List<ResponseBeverageItem> getBeverages();

    public ResponseBeverageItem getBeverage(Long id);

    public ResponseBeverageItem createBeverage(RequestCreateBeverage request);

    public ResponseBeverageItem settingCategory(long beverageId, long categoryId);
}
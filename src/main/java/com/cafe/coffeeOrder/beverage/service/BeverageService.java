package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;

import java.util.List;
import java.util.Optional;

public interface BeverageService {

    public List<ResponseBeverageItem> getBeverages();

    public ResponseBeverageItem getBeverage(long id);

    public void createBeverage(RequestCreateBeverage request);

    public void settingCategory(long categoryId);

}
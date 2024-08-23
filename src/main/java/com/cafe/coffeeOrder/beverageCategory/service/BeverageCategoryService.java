package com.cafe.coffeeOrder.beverageCategory.service;

import com.cafe.coffeeOrder.beverageCategory.dto.RequestCreateBeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.ResponseBeverageCategoryItem;

public interface BeverageCategoryService {

    public ResponseBeverageCategoryItem getBeverageCategory(long id);

    public ResponseBeverageCategoryItem createBeverageCategory(RequestCreateBeverageCategory request);

}
package com.cafe.coffeeOrder.beverageCategory.service;

import com.cafe.coffeeOrder.beverageCategory.dto.RequestCreateBeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.ResponseBeverageCategoryItem;
import com.cafe.coffeeOrder.beverageCategory.repository.BeverageCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BeverageCategoryServiceImpl implements BeverageCategoryService {

    private final BeverageCategoryRepository beverageCategoryRepository;

    @Autowired
    public BeverageCategoryServiceImpl(BeverageCategoryRepository beverageCategoryRepository) {
        this.beverageCategoryRepository = beverageCategoryRepository;
    }

    @Override
    public ResponseBeverageCategoryItem getBeverageCategory(long id) {
        return beverageCategoryRepository.selectCategoryById(id)
                .map(ResponseBeverageCategoryItem::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 존재하지 않음"));
    }

    @Override
    @Transactional
    public ResponseBeverageCategoryItem createBeverageCategory(RequestCreateBeverageCategory request) {
        return null;
    }
}
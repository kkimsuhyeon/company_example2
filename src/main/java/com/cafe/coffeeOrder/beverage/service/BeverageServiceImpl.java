package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.beverage.exception.BeverageException;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.exception.BeverageCategoryException;
import com.cafe.coffeeOrder.beverageCategory.repository.BeverageCategoryRepository;
import com.cafe.coffeeOrder.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BeverageServiceImpl implements BeverageService {

    private final BeverageRepository beverageRepository;
    private final BeverageCategoryRepository beverageCategoryRepository;

    @Autowired
    public BeverageServiceImpl(BeverageRepository beverageRepository, BeverageCategoryRepository beverageCategoryRepository) {
        this.beverageRepository = beverageRepository;
        this.beverageCategoryRepository = beverageCategoryRepository;
    }

    @Override
    public List<ResponseBeverageItem> getBeverages() {
        List<Beverage> beverages = beverageRepository.selectBeverages();
        return beverages.stream().map(ResponseBeverageItem::from).toList();
    }

    @Override
    public ResponseBeverageItem getBeverage(long id) {
        Optional<Beverage> result = beverageRepository.selectBeverageById(id);

        return result
                .map(ResponseBeverageItem::from)
                .orElseThrow(() -> new CustomException(BeverageException.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseBeverageItem createBeverage(RequestCreateBeverage request) {
        Beverage result = beverageRepository.insertBeverage(request.toEntity());
        if (request.getCategoryId() != 0L) {
            Optional<BeverageCategory> category = beverageCategoryRepository.selectCategoryById(request.getCategoryId());
            category.ifPresent(result::setCategory);
        }

        return ResponseBeverageItem.from(result);
    }

    @Override
    @Transactional
    public ResponseBeverageItem settingCategory(long beverageId, long categoryId) {
        Optional<Beverage> result = beverageRepository.selectBeverageById(beverageId);
        Beverage beverage = result.orElseThrow(() -> new CustomException(BeverageException.NOT_FOUND));

        Optional<BeverageCategory> category = beverageCategoryRepository.selectCategoryById(categoryId);
        category.ifPresentOrElse(beverage::setCategory, () -> {
            throw new CustomException(BeverageCategoryException.NOT_FOUND);
        });

        return ResponseBeverageItem.from(beverage);
    }
}
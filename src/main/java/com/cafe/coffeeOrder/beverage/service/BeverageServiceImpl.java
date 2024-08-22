package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BeverageServiceImpl implements BeverageService {

    private final BeverageRepository beverageRepository;

    @Autowired
    public BeverageServiceImpl(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    @Override
    @Transactional
    public void createBeverage(RequestCreateBeverage request) {
        beverageRepository.insertBeverage(request.toEntity());
    }

    @Override
    public void settingCategory(long categoryId) {

    }
}
package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BeverageRepository {

    public void insertBeverage(Beverage beverage);

    public List<Beverage> selectBeverages();

    public Optional<Beverage> selectBeverageById(long id);

}
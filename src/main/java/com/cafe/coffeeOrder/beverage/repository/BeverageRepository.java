package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BeverageRepository {

    public Beverage insertBeverage(Beverage beverage);

    public List<Beverage> selectBeverages();

    public Optional<Beverage> selectBeverageById(long id);

}
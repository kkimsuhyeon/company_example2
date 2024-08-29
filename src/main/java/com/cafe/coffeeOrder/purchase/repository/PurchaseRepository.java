package com.cafe.coffeeOrder.purchase.repository;

import com.cafe.coffeeOrder.purchase.domain.Purchase;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface PurchaseRepository {

    public List<Purchase> selectPurchases();

    public Optional<Purchase> selectPurchaseById(long id);

    public Purchase insertPurchase(Purchase purchase);

}
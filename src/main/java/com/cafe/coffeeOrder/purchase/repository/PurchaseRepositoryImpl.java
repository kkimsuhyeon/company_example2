package com.cafe.coffeeOrder.purchase.repository;

import com.cafe.coffeeOrder.purchase.domain.Purchase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Purchase> selectPurchases() {
        String query = "SELECT purchase FROM Purchase AS purchase" +
                " INNER JOIN FETCH purchase.receipt AS r" +
                " INNER JOIN FETCH purchase.beverage AS b";

        return entityManager.createQuery(query, Purchase.class).getResultList();
    }

    @Override
    public Optional<Purchase> selectPurchaseById(long id) {
        String query = "SELECT purchase FROM Purchase AS purchase" +
                " INNER JOIN FETCH purchase.receipt AS r" +
                " INNER JOIN FETCH purchase.beverage AS b" +
                " LEFT JOIN FETCH purchase.payments AS pay" +
                " WHERE purchase.id = :id";

        Purchase purchase = entityManager
                .createQuery(query, Purchase.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(purchase);
    }

    @Override
    public Purchase insertPurchase(Purchase purchase) {
        entityManager.persist(purchase);
        return purchase;
    }
}
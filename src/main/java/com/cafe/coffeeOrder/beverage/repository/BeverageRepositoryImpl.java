package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BeverageRepositoryImpl implements BeverageRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void insertBeverage(Beverage beverage) {
        entityManager.persist(beverage);
    }

    @Override
    public List<Beverage> selectBeverages() {
        String query = "SELECT b FROM Beverage AS b";

        return entityManager.createQuery(query, Beverage.class)
                .getResultList();
    }

    @Override
    public Optional<Beverage> selectBeverageById(long id) {
        String query = "SELECT b FROM Beverage AS b" +
                " WHERE b.id = :id";

        Beverage result = entityManager.createQuery(query, Beverage.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
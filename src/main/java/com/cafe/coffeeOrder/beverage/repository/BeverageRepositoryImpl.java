package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
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
    public Beverage insertBeverage(Beverage beverage) {
        entityManager.persist(beverage);
        return beverage;
    }

    @Override
    public List<Beverage> selectBeverages() {
        String query = "SELECT b FROM Beverage AS b" +
                " LEFT JOIN FETCH b.category AS bc";

        return entityManager.createQuery(query, Beverage.class)
                .getResultList();
    }

    @Override
    public Optional<Beverage> selectBeverageById(long id) {
        String query = "SELECT b FROM Beverage AS b" +
                " LEFT JOIN FETCH b.category AS bc" +
                " WHERE b.id = :id";

        List<Beverage> result = entityManager.createQuery(query, Beverage.class)
                .setParameter("id", id)
                .getResultList();

        return result.stream().findFirst();
    }
}
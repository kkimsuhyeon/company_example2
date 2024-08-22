package com.cafe.coffeeOrder.beverageCategory.repository;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.SearchCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BeverageCategoryRepositoryImpl implements BeverageCategoryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public BeverageCategory insertCategory(BeverageCategory category) {
        entityManager.persist(category);
        return category;
    }

    @Override
    public Optional<BeverageCategory> selectCategoryById(Long id) {
        String query = "SELECT bc FROM BeverageCategory AS bc" +
                " WHERE bc.id = :id";

        BeverageCategory result = entityManager.createQuery(query, BeverageCategory.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public List<BeverageCategory> selectCategories(SearchCategory search) {
        String query = "SELECT bc FROM BeverageCategory AS bc";
        String name = search.getTitle();

        if (name != null && !name.isEmpty()) {
            query += " WHERE bc.name LIKE '%%%s%%'".formatted(name);
        }

        return entityManager.createQuery(query, BeverageCategory.class)
                .getResultList();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Optional<BeverageCategory> result = selectCategoryById(categoryId);

        result.ifPresent(category -> entityManager.remove(category));
    }
}
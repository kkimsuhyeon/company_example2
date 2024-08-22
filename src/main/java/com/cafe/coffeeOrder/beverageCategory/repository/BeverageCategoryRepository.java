package com.cafe.coffeeOrder.beverageCategory.repository;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.SearchCategory;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BeverageCategoryRepository {

    public BeverageCategory insertCategory(BeverageCategory category);

    public Optional<BeverageCategory> selectCategoryById(Long id);

    public List<BeverageCategory> selectCategories(SearchCategory search);

    public void deleteCategory(Long categoryId);

}
package com.cafe.coffeeOrder.beverageCategory.repository;

import com.cafe.coffeeOrder.beverageCategory.domain.BeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.SearchCategory;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BeverageCategoryRepositoryTest {

    @Autowired
    BeverageCategoryRepository sut;

    @Test
    @DisplayName("음료 카테고리 추가")
    void insert_category() {
        long newId = 7L;
        String categoryName = "category4";
        BeverageCategory beverageCategory = BeverageCategory.of(categoryName);

        sut.insertCategory(beverageCategory);
        BeverageCategory actual = sut.selectCategoryById(newId).get();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", newId)
                .hasFieldOrPropertyWithValue("name", categoryName);
    }

    @Test
    @DisplayName("음료 카테고리 생성 시, 이름 누락")
    void insert_category_no_name() {
        BeverageCategory beverageCategory = BeverageCategory.of(null);

        assertThatThrownBy(() -> sut.insertCategory(beverageCategory)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("음료 카테고리 id를 통해 검색")
    void select_category_by_id() {
        BeverageCategory actual = sut.selectCategoryById(2L).get();

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("name", "category2");
    }

    @Test
    @DisplayName("카테고리 id로 검색 시, 해당 id에 맞는 카테고리가 존재하지 않음")
    void select_category_by_id_fail() {
        long id = 100L;

        assertThatThrownBy(() -> sut.selectCategoryById(id)).isInstanceOf(NoResultException.class);
    }

    @Test
    @DisplayName("음료 카테고리 전체")
    void select_categories() {
        List<BeverageCategory> actual = sut.selectCategories(new SearchCategory());

        assertThat(actual).hasSize(6);
    }

    @Test
    @DisplayName("음료 카테고리 이름을 통해 검색")
    void select_category_by_name() {
        String searchName = "category1";
        SearchCategory searchValue = new SearchCategory();
        searchValue.setTitle(searchName);

        List<BeverageCategory> actual = sut.selectCategories(searchValue);

        assertThat(actual).hasSize(1).allMatch((category) -> {
            return category.getName().equals(searchName);
        });
    }

    @Test
    @DisplayName("음료 카테고리 삭제")
    void delete_category() {
        long id = 1L;

        sut.deleteCategory(id);

        assertThatThrownBy(() -> sut.selectCategoryById(id)).isInstanceOf(ConstraintViolationException.class);
    }

}


class TestJpaConfig {

    @Bean
    public BeverageCategoryRepository beverageRepository() {
        return new BeverageCategoryRepositoryImpl();
    }

}
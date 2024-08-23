package com.cafe.coffeeOrder.beverageCategory;

import com.cafe.coffeeOrder.beverageCategory.dto.ResponseBeverageCategoryItem;
import com.cafe.coffeeOrder.beverageCategory.repository.BeverageCategoryRepository;
import com.cafe.coffeeOrder.beverageCategory.service.BeverageCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class BeverageCategoryTest {

    @Autowired
    BeverageCategoryService sut;

    @Test
    @DisplayName("음료 카테고리 조회")
    void get_category_by_id() {
        long id = 1L;

        ResponseBeverageCategoryItem actual = sut.getBeverageCategory(id);

        assertThat(actual).hasFieldOrPropertyWithValue("name", "category1");
    }

    @Test
    @DisplayName("음료 카테고리 생성")
    void create_category() {

    }

}

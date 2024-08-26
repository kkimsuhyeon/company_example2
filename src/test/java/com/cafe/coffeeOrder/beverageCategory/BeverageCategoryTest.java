package com.cafe.coffeeOrder.beverageCategory;

import com.cafe.coffeeOrder.beverageCategory.dto.RequestCreateBeverageCategory;
import com.cafe.coffeeOrder.beverageCategory.dto.ResponseBeverageCategoryItem;
import com.cafe.coffeeOrder.beverageCategory.service.BeverageCategoryService;
import com.cafe.coffeeOrder.common.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BeverageCategoryTest {

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
    @DisplayName("음료 카테고리 조회 시, 해당 카테고리가 존재하지 않음")
    void get_category_by_id_fail() {
        assertThatThrownBy(() -> sut.getBeverageCategory(100L))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                .hasFieldOrPropertyWithValue("errorCode", "CTG-001");
    }

    @Test
    @DisplayName("음료 카테고리 생성")
    void create_category() {
        String name = "test";
        RequestCreateBeverageCategory request = RequestCreateBeverageCategory.builder().name(name).build();

        ResponseBeverageCategoryItem actual = sut.createBeverageCategory(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 7L)
                .hasFieldOrPropertyWithValue("name", name);
    }

}

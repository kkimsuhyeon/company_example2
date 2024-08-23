package com.cafe.coffeeOrder.beverage;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.beverage.service.BeverageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BeverageTest {

    @Autowired
    BeverageService sut;

    @Test
    @DisplayName("음료 생성, 카테고리는 없음")
    void create_beverage_no_category() {
        RequestCreateBeverage request = RequestCreateBeverage.builder().name("test").build();

        ResponseBeverageItem actual = sut.createBeverage(request);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("category", null);
    }

    @Test
    @DisplayName("음료 생성, 카테고리도 함께")
    void create_beverage_with_category() {
        RequestCreateBeverage request = RequestCreateBeverage.builder().name("test").categoryId(1L).build();

        ResponseBeverageItem actual = sut.createBeverage(request);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("name", "test");
        assertThat(actual.getCategory()).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "category1");
    }

    @Test
    @DisplayName("음료 전체 조회")
    void get_beverages() {
        List<ResponseBeverageItem> actual = sut.getBeverages();
        assertThat(actual).hasSize(4);
    }

    @Test
    @DisplayName("음료 id를 통해서 조회")
    void get_beverage_by_id() {
        ResponseBeverageItem beverageNoCategory = sut.getBeverage(1L);
        ResponseBeverageItem beverageWithCategory = sut.getBeverage(3L);

        assertThat(beverageNoCategory)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "beverage1");
        assertThat(beverageWithCategory)
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("name", "beverage3");
        assertThat(beverageWithCategory.getCategory()).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("음료 카테고리 설정")
    void set_beverage_category() {
        ResponseBeverageItem actual = sut.settingCategory(1L, 2L);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("name", "beverage1");
        assertThat(actual.getCategory()).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("name", "category2");
    }
}

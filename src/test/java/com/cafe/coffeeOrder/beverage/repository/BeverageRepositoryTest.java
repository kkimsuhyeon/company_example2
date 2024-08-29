package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BeverageRepositoryTest {

    // 에러 나오는 테스트 추가 ( status 바뀌는 경우 )

    @Autowired
    BeverageRepository sut;

    @Test
    @DisplayName("음료 추가")
    void insert_beverage() {
        long newId = 5L;
        String newBeverageName = "new beverage";
        int price = 3000;
        Beverage newBeverage = Beverage.of(newBeverageName, price);

        Beverage actual = sut.insertBeverage(newBeverage);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", newId)
                .hasFieldOrPropertyWithValue("price", price)
                .hasFieldOrPropertyWithValue("name", newBeverageName);
    }

    @Test
    @DisplayName("음료 추가 시, 이름 누락")
    void insert_beverage_no_name() {
        Beverage newBeverage = Beverage.of(null, 3000);

        assertThatThrownBy(() -> sut.insertBeverage(newBeverage))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("음료 추가 시, 가격 누락")
    void insert_beverage_no_price() {
        Beverage newBeverage = Beverage.of("null", null);

        assertThatThrownBy(() -> sut.insertBeverage(newBeverage))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("음료 전체 리스트")
    void select_beverages() {
        List<Beverage> actual = sut.selectBeverages();

        assertThat(actual).hasSize(4);

        assertThat(actual.get(2))
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("price", 2000);

        assertThat(actual.get(2).getCategory())
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("음료 id로 검색")
    void select_beverage_by_id() {
        Beverage actual = sut.selectBeverageById(3L).get();

        assertThat(actual).hasFieldOrPropertyWithValue("name", "beverage3")
                .hasFieldOrPropertyWithValue("price", 2000);

        assertThat(actual.getCategory())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "category1");
    }

}

class TestJpaConfig {

    @Bean
    public BeverageRepository beverageRepository() {
        return new BeverageRepositoryImpl();
    }

}
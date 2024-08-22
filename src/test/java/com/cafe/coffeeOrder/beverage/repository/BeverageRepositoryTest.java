package com.cafe.coffeeOrder.beverage.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({JpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BeverageRepositoryTest {

    @Autowired
    BeverageRepository sut;

    @Test
    @DisplayName("음료 추가")
    void insert_beverage() {
        long newId = 5L;
        String newBeverageName = "new beverage";
        Beverage newBeverage = Beverage.of("new beverage");

        sut.insertBeverage(newBeverage);
        Beverage actual = sut.selectBeverageById(newId).get();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", newId)
                .hasFieldOrPropertyWithValue("name", newBeverageName);
    }

    @Test
    @DisplayName("음료 추가 시, 이름 누락")
    void insert_beverage_no_name() {
        Beverage newBeverage = Beverage.of(null);

        assertThatThrownBy(() -> sut.insertBeverage(newBeverage)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("음료 전체 리스트")
    void select_beverages() {
        List<Beverage> actual = sut.selectBeverages();

        assertThat(actual).hasSize(4);
    }

    @Test
    @DisplayName("음료 id로 검색")
    void select_beverage_by_id() {
        Beverage actual = sut.selectBeverageById(1L).get();

        assertThat(actual).hasFieldOrPropertyWithValue("name", "beverage1");
    }

}

class JpaConfig {

    @Bean
    public BeverageRepository beverageRepository() {
        return new BeverageRepositoryImpl();
    }

}
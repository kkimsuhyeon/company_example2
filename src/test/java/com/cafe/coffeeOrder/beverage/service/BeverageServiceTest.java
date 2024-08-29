package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class BeverageServiceTest {
    @InjectMocks
    private BeverageServiceImpl sut;

    @Mock
    private BeverageRepository beverageRepository;

    @Test
    @DisplayName("음료 전체 조회")
    void get_beverages() {
        Beverage beverage1 = Beverage.of("name1", 3000);
        Beverage beverage2 = Beverage.of("name2", 4000);
        Beverage beverage3 = Beverage.of("name3", 5000);

        given(beverageRepository.selectBeverages()).willReturn(List.of(beverage1, beverage2, beverage3));

        List<ResponseBeverageItem> actual = sut.getBeverages();

        assertThat(actual).hasSize(3);
    }
}
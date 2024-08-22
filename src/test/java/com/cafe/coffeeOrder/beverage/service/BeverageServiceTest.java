package com.cafe.coffeeOrder.beverage.service;

import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeverageServiceTest {

    @InjectMocks
    private BeverageService beverageService;

    @Mock
    private BeverageRepository beverageRepository;

    @Test
    @DisplayName("음료 생성")
    void create_beverage() {

    }

}
package com.cafe.coffeeOrder.orders;


import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.orders.dto.RequestCreateOrders;
import com.cafe.coffeeOrder.orders.dto.ResponseOrdersItem;
import com.cafe.coffeeOrder.orders.service.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OrderTest {

    @Autowired
    OrdersService sut;

    @Test
    @DisplayName("주문 조회")
    void getOrders() {
        ResponseOrdersItem actual = sut.getOrderItem(1L);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("해당 주문의 결제 내역 조회")
    void getOrdersWithPayment() {
        ResponseOrdersItem actual = sut.getOrderItem(3L);

        assertThat(actual).hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
        assertThat(actual.getPayments()).isNotNull().hasSize(2);
    }

    @Test
    @DisplayName("주문 생성")
    void create_order() {
        RequestCreateOrders request = RequestCreateOrders.builder().customerId(2L).beverageId(2L).build();
        ResponseOrdersItem actual = sut.createOrder(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 9L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
        assertThat(actual.getCustomer())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "customer2");
        assertThat(actual.getBeverage())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "beverage2");
        assertThat(actual.getPayments()).isNotNull().hasSize(0);
    }


    @Test
    @DisplayName("주문 배송")
    void send_beverage() {
        ResponseOrdersItem actual = sut.sendBeverage(1L);

        assertThat(actual).hasFieldOrPropertyWithValue("status", OrdersStatus.DELIVERY_COMPLETE);
    }

    @Test
    @DisplayName("주문 배송 실패")
    void send_beverage_fail() {
        assertThatThrownBy(() -> sut.sendBeverage(3L)).isInstanceOf(IllegalArgumentException.class);
    }
}

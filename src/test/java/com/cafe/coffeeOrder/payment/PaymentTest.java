package com.cafe.coffeeOrder.payment;

import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestCreatePayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithOrder;
import com.cafe.coffeeOrder.payment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PaymentTest {

    @Autowired
    PaymentService sut;

    @Test
    @DisplayName("결제 내역 조회")
    void get_payment_by_id() {
        long id = 1L;

        ResponsePaymentWithOrder actual = sut.getPayment(id);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(actual.getOrders())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 성공")
    void success_payment() {
        ResponsePaymentWithOrder actual = sut.successPayment(5L);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(actual.getOrders())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 실패")
    void fail_payment() {
        ResponsePaymentWithOrder actual = sut.failPayment(5L);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);
        assertThat(actual.getOrders())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
    }

    @Test
    @DisplayName("결제 내역 생성")
    void create_payment() {
        RequestCreatePayment requestSuccess = RequestCreatePayment.builder().orderId(5L).status(PaymentStatus.SUCCESS).build();
        RequestCreatePayment requestFail = RequestCreatePayment.builder().orderId(6L).status(PaymentStatus.FAIL).build();

        ResponsePaymentWithOrder actualSuccess = sut.createPayment(requestSuccess);
        ResponsePaymentWithOrder actualFail = sut.createPayment(requestFail);

        assertThat(actualSuccess).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(actualSuccess.getOrders())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.SUCCESS);

        assertThat(actualFail).hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);
        assertThat(actualFail.getOrders())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
    }
}

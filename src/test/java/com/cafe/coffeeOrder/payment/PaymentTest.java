package com.cafe.coffeeOrder.payment;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestRegistPayment;
import com.cafe.coffeeOrder.payment.dto.ResponsePaymentWithPurchase;
import com.cafe.coffeeOrder.payment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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

        ResponsePaymentWithPurchase actual = sut.getPayment(id);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS)
                .hasFieldOrPropertyWithValue("price", 3000);
        assertThat(actual.getPurchase())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 내역 생성")
    void regist_payment() {
        RequestRegistPayment requestFail = RequestRegistPayment.of(11L, 3000, PaymentMethod.CARD, PaymentStatus.FAIL);
        RequestRegistPayment requestSuccess = RequestRegistPayment.of(11L, 1000, PaymentMethod.CARD, PaymentStatus.SUCCESS);

        ResponsePaymentWithPurchase actualFail = sut.registPayment(requestFail);
        ResponsePaymentWithPurchase actualSuccess = sut.registPayment(requestSuccess);

        assertThat(actualFail)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("price", 3000)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);

        assertThat(actualSuccess)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("price", 1000)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 내역 여러개 생성")
    void regist_multi_payment() {
        RequestRegistPayment requestFail = RequestRegistPayment.of(11L, 3000, PaymentMethod.CARD, PaymentStatus.FAIL);
        RequestRegistPayment requestSuccess = RequestRegistPayment.of(11L, 1000, PaymentMethod.CARD, PaymentStatus.SUCCESS);
        List<RequestRegistPayment> requests = List.of(requestFail, requestSuccess);

        List<ResponsePaymentWithPurchase> actual = sut.registMultiPayment(requests);

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("price", 3000)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);
        assertThat(actual.get(1))
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("price", 1000)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
    }

}

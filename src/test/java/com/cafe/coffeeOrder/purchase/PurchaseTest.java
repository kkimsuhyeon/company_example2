package com.cafe.coffeeOrder.purchase;


import com.cafe.coffeeOrder.common.exception.CustomException;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.purchase.dto.RequestCreatePurchase;
import com.cafe.coffeeOrder.purchase.dto.RequestPayPurchase;
import com.cafe.coffeeOrder.purchase.dto.ResponsePurchaseItem;
import com.cafe.coffeeOrder.purchase.service.PurchaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PurchaseTest {

    @Autowired
    PurchaseService sut;

    @Test
    @DisplayName("주문 조회")
    void get_purchase() {
        ResponsePurchaseItem actual = sut.getPurchase(1L);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 1L);
    }


    @Test
    @DisplayName("주문 생성")
    void create_order() {
        RequestCreatePurchase request = RequestCreatePurchase.of(2L, 2L);

        ResponsePurchaseItem actual = sut.createPurchase(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 12L)
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT);
        assertThat(actual.getBeverage())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "beverage2");
        assertThat(actual.getPayments()).isNotNull().hasSize(0);
    }

    @Test
    @DisplayName("주문 결제")
    void pay_purchase() {
        RequestCreatePurchase createRequest = RequestCreatePurchase.of(2L, 2L);
        ResponsePurchaseItem purchase = sut.createPurchase(createRequest);

        RequestPayPurchase request = RequestPayPurchase.of(purchase.getId(), 1500, PaymentMethod.CARD);

        ResponsePurchaseItem actual = sut.payPurchase(request);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
        assertThat(actual.getPayments())
                .hasSize(1)
                .last()
                .hasFieldOrPropertyWithValue("method", PaymentMethod.CARD)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS)
                .hasFieldOrPropertyWithValue("price", 1500);
    }

    @Test
    @DisplayName("주문 결제 실패")
    void pay_purchase_fail() {
        RequestCreatePurchase createRequest = RequestCreatePurchase.of(2L, 2L);
        ResponsePurchaseItem purchase = sut.createPurchase(createRequest);

        RequestPayPurchase request = RequestPayPurchase.of(purchase.getId(), 3000, PaymentMethod.CARD);

        assertThatThrownBy(() -> sut.payPurchase(request)).isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", "PAY-002");

        ResponsePurchaseItem actual = sut.getPurchase(purchase.getId());

        assertThat(actual).hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT);
        assertThat(actual.getPayments()).last().hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);
    }


    @Test
    @DisplayName("해당 주문의 결제 내역 조회")
    void get_purchase_with_payment() {
        RequestCreatePurchase createRequest = RequestCreatePurchase.of(2L, 2L);
        ResponsePurchaseItem purchase = sut.createPurchase(createRequest);

        RequestPayPurchase request1 = RequestPayPurchase.of(purchase.getId(), 3000, PaymentMethod.CARD);
        RequestPayPurchase request2 = RequestPayPurchase.of(purchase.getId(), 2000, PaymentMethod.CARD);
        RequestPayPurchase request3 = RequestPayPurchase.of(purchase.getId(), 1500, PaymentMethod.CARD);

        assertThatThrownBy(() -> sut.payPurchase(request1)).isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", "PAY-002");

        assertThatThrownBy(() -> sut.payPurchase(request2)).isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", "PAY-002");
        sut.payPurchase(request3);

        ResponsePurchaseItem actual = sut.getPurchase(purchase.getId());

        assertThat(actual)
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
        assertThat(actual.getPayments())
                .hasSize(3)
                .first()
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);

    }


    @Test
    @DisplayName("주문 배송")
    void send_beverage() {
        ResponsePurchaseItem actual = sut.sendBeverage(1L);

        assertThat(actual).hasFieldOrPropertyWithValue("status", PurchaseStatus.DELIVERY_COMPLETE);
    }

    @Test
    @DisplayName("주문 배송 실패")
    void send_beverage_fail() {
        assertThatThrownBy(() -> sut.sendBeverage(3L)).isInstanceOf(IllegalArgumentException.class);
    }
}

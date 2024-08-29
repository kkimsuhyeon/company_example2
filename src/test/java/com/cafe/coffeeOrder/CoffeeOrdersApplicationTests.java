package com.cafe.coffeeOrder;

import com.cafe.coffeeOrder.customer.service.CustomerService;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.purchase.dto.ResponsePurchaseItem;
import com.cafe.coffeeOrder.purchase.service.PurchaseService;
import com.cafe.coffeeOrder.payment.service.PaymentService;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import com.cafe.coffeeOrder.receipt.dto.RequestCreateReceipt;
import com.cafe.coffeeOrder.receipt.dto.RequestPayReceipt;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceiptItem;
import com.cafe.coffeeOrder.receipt.service.ReceiptService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoffeeOrdersApplicationTests {

    @Autowired
    CustomerService customerService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    PurchaseService ordersService;


    @Test
    @Rollback(value = false)
    @DisplayName("영수증 생성 -> 주문 생성")
    @Order(1)
    void create_receipt() {
        RequestCreateReceipt request = new RequestCreateReceipt.Builder(2L, List.of(1L, 1L, 3L, 4L)).build();

        ResponseReceiptItem actual = receiptService.createReceipt(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT)
                .hasFieldOrPropertyWithValue("totalPrice", 6500);

        assertThat(actual.getCustomer())
                .hasFieldOrPropertyWithValue("name", "customer2");

        assertThat(actual.getPurchases()).hasSize(4);

        assertThat(actual.getPurchases().get(0))
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT)
                .hasFieldOrPropertyWithValue("price", 1000);

        assertThat(actual.getPurchases().get(0).getBeverage())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "beverage1")
                .hasFieldOrPropertyWithValue("price", 1000);

        assertThat(actual.getPurchases().get(0).getPayments()).hasSize(0);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("결제 성공")
    @Order(2)
    void success_payment() {
        RequestPayReceipt request = RequestPayReceipt.of(6L, List.of(12L), 1000, PaymentMethod.CASH);
        receiptService.payReceipt(request);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("결제 실패")
    @Order(3)
    void fail_payment() {
        RequestPayReceipt request = RequestPayReceipt.of(6L, List.of(13L, 14L), 100000, PaymentMethod.CASH);
        receiptService.payReceipt(request);
    }

    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("결제 전체 성공")
    @Order(4)
    void success_all_payment() {

    }

    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("음료 배송")
    @Order(5)
    void send_beverage() {
//        ResponsePurchaseItem actual = ordersService.sendBeverage(10L);
//
//        assertThat(actual).isNotNull()
//                .hasFieldOrPropertyWithValue("id", 10L)
//                .hasFieldOrPropertyWithValue("status", PurchaseStatus.DELIVERY_COMPLETE);
//
//        assertThat(actual.getReceipt())
//                .isNotNull()
//                .hasFieldOrPropertyWithValue("id", 6L)
//                .hasFieldOrPropertyWithValue("status", ReceiptStatus.SUCCESS);
    }

    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("음료 전체 배송")
    @Order(6)
    void send_beverage_all() {
//        ordersService.sendBeverage(11L);
//        ordersService.sendBeverage(12L);
//        ordersService.sendBeverage(13L);
//
//        ResponseReceiptItem actual = receiptService.getReceipt(6L);
//
//        assertThat(actual).isNotNull()
//                .hasFieldOrPropertyWithValue("id", 6L)
//                .hasFieldOrPropertyWithValue("status", ReceiptStatus.FINISH);
//
//        assertThat(actual.getPurchases())
//                .allMatch((item) -> item.getStatus().equals(PurchaseStatus.DELIVERY_COMPLETE));
    }


}

package com.cafe.coffeeOrder.receipt;

import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.purchase.dto.RequestPayPurchase;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import com.cafe.coffeeOrder.receipt.dto.RequestCreateReceipt;
import com.cafe.coffeeOrder.receipt.dto.RequestPayReceipt;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceiptItem;
import com.cafe.coffeeOrder.receipt.service.ReceiptService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ReceiptTest {

    @Autowired
    ReceiptService sut;

    @Test
    @DisplayName("영수증 id로 조회")
    void get_receipt_by_id() {
        ResponseReceiptItem actual = sut.getReceipt(1L);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT)
                .hasFieldOrPropertyWithValue("totalPrice", 5000);
    }

    @Test
    @DisplayName("영수증 생성")
    void create_receipt() {
        RequestCreateReceipt request = new RequestCreateReceipt.Builder(4L, List.of(1L, 1L, 3L, 4L)).build();

        ResponseReceiptItem actual = sut.createReceipt(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT);
        assertThat(actual.getCustomer())
                .hasFieldOrPropertyWithValue("id", 4L)
                .hasFieldOrPropertyWithValue("name", "customer4");
        assertThat(actual.getPurchases()).hasSize(4);
        assertThat(actual.getPurchases().get(0).getBeverage())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "beverage1");
        assertThat(actual.getPurchases().get(0).getPayments()).hasSize(0);
    }

    @Test
    @DisplayName("1개 결제")
    void pay_one() {
        RequestCreateReceipt requestCreateReceipt = new RequestCreateReceipt.Builder(4L, List.of(1L, 1L, 3L, 4L)).build();
        ResponseReceiptItem receipt = sut.createReceipt(requestCreateReceipt);

        RequestPayReceipt request = RequestPayReceipt.of(receipt.getId(), List.of(12L), 1000, PaymentMethod.CARD);

        ResponseReceiptItem actual = sut.payReceipt(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT);

        assertThat(actual.getPurchases()).hasSize(4);
        assertThat(actual.getPurchases().get(0))
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);

        assertThat(actual.getPurchases().get(0).getPayments()).hasSize(1);
        assertThat(actual.getPurchases().get(0).getPayments().get(0))
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
    }

    @Test
    @DisplayName("2개 결제")
    void pay_two() {
        RequestCreateReceipt requestCreateReceipt = new RequestCreateReceipt.Builder(4L, List.of(1L, 1L, 3L, 4L)).build();
        ResponseReceiptItem receipt = sut.createReceipt(requestCreateReceipt);

        RequestPayReceipt request = RequestPayReceipt.of(receipt.getId(), List.of(12L, 13L), 2000, PaymentMethod.CASH);

        ResponseReceiptItem actual = sut.payReceipt(request);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT);

        assertThat(actual.getPurchases()).hasSize(4);
        assertThat(actual.getPurchases().get(0))
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
        assertThat(actual.getPurchases().get(0).getPayments()).hasSize(1);
        assertThat(actual.getPurchases().get(0).getPayments().get(0))
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);

        assertThat(actual.getPurchases().get(1))
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
        assertThat(actual.getPurchases().get(1).getPayments()).hasSize(1);
        assertThat(actual.getPurchases().get(1).getPayments().get(0))
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);

        assertThat(actual.getPurchases().get(2))
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT);
        assertThat(actual.getPurchases().get(2).getPayments()).hasSize(0);
    }


}

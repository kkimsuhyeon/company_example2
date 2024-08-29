package com.cafe.coffeeOrder.receipt.repository;

import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.domain.constant.ReceiptStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ReceiptRepositoryTest {

    @Autowired
    ReceiptRepository sut;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("전체 영수증 출력")
    void select_receipts() {
        List<Receipt> actual = sut.selectReceipts();

        assertThat(actual).hasSize(5);
    }

    @Test
    @DisplayName("영수증 id를 통해서 검색")
    void select_receipt_by_id() {
        Receipt actual = sut.selectReceiptById(1L).get();

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("totalPrice", 5000);
        assertThat(actual.getCustomer()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actual.getPurchases()).hasSize(5);
        assertThat(actual.getPurchases().get(0).getPayments()).hasSize(1);
        assertThat(actual.getPurchases().get(0).getBeverage()).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("영수증 생성")
    void insert_receipt() {
        Customer customer = Customer.of("test");
        entityManager.persist(customer);
        Receipt receipt = Receipt.of(customer);

        Receipt actual = sut.insertReceipt(receipt);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("status", ReceiptStatus.WAIT)
                .hasFieldOrPropertyWithValue("totalPrice", 0);
    }

}

@EnableJpaAuditing
class TestJpaConfig {

    @Bean
    public ReceiptRepository receiptRepository() {
        return new ReceiptRepositoryImpl();
    }

}
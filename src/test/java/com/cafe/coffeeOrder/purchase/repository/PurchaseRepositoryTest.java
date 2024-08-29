package com.cafe.coffeeOrder.purchase.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepositoryImpl;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.repository.ReceiptRepository;
import com.cafe.coffeeOrder.receipt.repository.ReceiptRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PurchaseRepositoryTest {

    @Autowired
    PurchaseRepository sut;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("주문 전체 조회")
    void select_orders() {
        List<Purchase> actual = sut.selectPurchases();

        assertThat(actual).hasSize(11);
    }

    @Test
    @DisplayName("주문 id로 조회")
    void select_order_by_id() {
        long id = 1L;

        Purchase actual = sut.selectPurchaseById(id).get();

        assertThat(actual).hasFieldOrPropertyWithValue("id", id);
        assertThat(actual.getBeverage()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actual.getReceipt()).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("주문 생성")
    @Transactional
    void insert_order() {
        Customer customer = Customer.of("customer");
        entityManager.persist(customer);

        Beverage beverage = Beverage.of("beverage", 3000);
        Beverage beverage2 = Beverage.of("beverage2", 3000);
        Receipt receipt = Receipt.of(customer);
        entityManager.persist(beverage);
        entityManager.persist(beverage2);
        entityManager.persist(receipt);

        Purchase purchase1 = Purchase.of(receipt, beverage);
        sut.insertPurchase(purchase1);

        Purchase purchase2 = Purchase.of(receipt, beverage2);
        Purchase actual = sut.insertPurchase(purchase2);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 13L)
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT)
                .hasFieldOrPropertyWithValue("price", 3000);
        assertThat(actual.getReceipt())
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("totalPrice", 6000);
        assertThat(actual.getBeverage())
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("price", 3000);
    }
}

@EnableJpaAuditing
class TestJpaConfig {

    @Bean
    public PurchaseRepository orderRepository() {
        return new PurchaseRepositoryImpl();
    }

}
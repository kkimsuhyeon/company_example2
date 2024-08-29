package com.cafe.coffeeOrder.payment.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentMethod;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.domain.constant.PurchaseStatus;
import com.cafe.coffeeOrder.purchase.repository.PurchaseRepository;
import com.cafe.coffeeOrder.purchase.repository.PurchaseRepositoryImpl;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PaymentRepositoryTest {

    @Autowired
    PaymentRepository sut;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("결제 성공")
    void insert_payment_success() {
        Customer customer = Customer.of("test");
        entityManager.persist(customer);

        Receipt receipt = Receipt.of(customer);
        entityManager.persist(receipt);

        Beverage beverage = Beverage.of("beverage", 3000);
        entityManager.persist(beverage);

        Purchase orders = Purchase.of(receipt, beverage);
        entityManager.persist(orders);

        Payment payment = Payment.of(orders, 3000, PaymentMethod.CARD, PaymentStatus.SUCCESS);

        Payment actual = sut.insertPayment(payment);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("price", 3000)
                .hasFieldOrPropertyWithValue("method", PaymentMethod.CARD)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(orders).hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 실패")
    void insert_payment_fail() {
        Customer customer = Customer.of("test");
        entityManager.persist(customer);

        Receipt receipt = Receipt.of(customer);
        entityManager.persist(receipt);

        Beverage beverage = Beverage.of("beverage", 3000);
        entityManager.persist(beverage);

        Purchase orders = Purchase.of(receipt, beverage);
        entityManager.persist(orders);

        Payment payment = Payment.of(orders, 3000, PaymentMethod.CARD, PaymentStatus.FAIL);

        Payment actual = sut.insertPayment(payment);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("price", 3000)
                .hasFieldOrPropertyWithValue("method", PaymentMethod.CARD)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.FAIL);
        assertThat(orders).hasFieldOrPropertyWithValue("status", PurchaseStatus.WAIT);
    }

    @Test
    @DisplayName("결제 id로 조회")
    void select_payment_by_id() {
        long id = 1L;

        Payment actual = sut.selectPaymentById(id).get();

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(actual.getPurchase())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", PurchaseStatus.SUCCESS);
    }


}

@EnableJpaAuditing
class TestJpaConfig {

    @Bean
    public PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl();
    }

}
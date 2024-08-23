package com.cafe.coffeeOrder.payment.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.orders.repository.OrdersRepository;
import com.cafe.coffeeOrder.orders.repository.OrdersRepositoryImpl;
import com.cafe.coffeeOrder.payment.domain.Payment;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
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
        Customer customer = Customer.of("customer");
        Beverage beverage = Beverage.of("beverage");
        Orders orders = Orders.of(customer, beverage);
        entityManager.persist(customer);
        entityManager.persist(beverage);
        entityManager.persist(orders);
        Payment payment = Payment.of(orders, PaymentStatus.SUCCESS);

        Payment actual = sut.insertPayment(payment);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L);
        assertThat(orders).hasFieldOrPropertyWithValue("status", OrdersStatus.SUCCESS);
    }

    @Test
    @DisplayName("결제 실패")
    void insert_payment_fail() {
        Customer customer = Customer.of("customer");
        Beverage beverage = Beverage.of("beverage");
        Orders orders = Orders.of(customer, beverage);
        entityManager.persist(customer);
        entityManager.persist(beverage);
        entityManager.persist(orders);
        Payment payment = Payment.of(orders, PaymentStatus.FAIL);

        Payment actual = sut.insertPayment(payment);

        assertThat(actual).hasFieldOrPropertyWithValue("id", 5L);
        assertThat(orders).hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
    }

    @Test
    @DisplayName("결제 id로 조회")
    void select_payment_by_id() {
        long id = 1L;

        Payment actual = sut.selectPaymentById(id).get();

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("status", PaymentStatus.SUCCESS);
        assertThat(actual.getOrders())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.SUCCESS);
    }


}

@EnableJpaAuditing
class TestJpaConfig {

    @Bean
    public PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl();
    }

    @Bean
    public OrdersRepository orderRepository() {
        return new OrdersRepositoryImpl();
    }
}
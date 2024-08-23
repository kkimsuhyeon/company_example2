package com.cafe.coffeeOrder.orders.repository;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepositoryImpl;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
import com.cafe.coffeeOrder.customer.repository.CustomerRepositoryImpl;
import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OrderRepositoryTest {

    @Autowired
    OrdersRepository sut;

    @Autowired
    BeverageRepository beverageRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("주문 전체 조회")
    void select_orders() {
        List<Orders> actual = sut.selectOrders();

        assertThat(actual).hasSize(8);
    }

    @Test
    @DisplayName("주문 id로 조회")
    void select_order_by_id() {
        long id = 1L;

        Orders actual = sut.selectOrderById(id).get();

        assertThat(actual).hasFieldOrPropertyWithValue("id", id);
        assertThat(actual.getBeverage()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actual.getCustomer()).hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("주문 생성")
    void insert_order() {
        Beverage beverage = beverageRepository.selectBeverageById(3L).get();
        Customer customer = customerRepository.selectCustomerById(2L).get();
        Orders orders = Orders.of(customer, beverage);

        Orders actual = sut.insertOrders(orders);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 9L)
                .hasFieldOrPropertyWithValue("status", OrdersStatus.WAIT);
        assertThat(actual.getCustomer()).hasFieldOrPropertyWithValue("id", 2L);
        assertThat(actual.getBeverage()).hasFieldOrPropertyWithValue("id", 3L);
    }


}

@EnableJpaAuditing
class TestJpaConfig {

    @Bean
    public OrdersRepository orderRepository() {
        return new OrdersRepositoryImpl();
    }

    @Bean
    public BeverageRepository beverageRepository() {
        return new BeverageRepositoryImpl();
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepositoryImpl();
    }
}
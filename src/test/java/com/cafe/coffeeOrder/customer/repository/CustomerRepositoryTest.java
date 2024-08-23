package com.cafe.coffeeOrder.customer.repository;

import com.cafe.coffeeOrder.customer.domain.Customer;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import({TestJpaConfig.class})
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository sut;

    @Test
    @DisplayName("고객 추가")
    void insert_customer() {
        long newCustomerId = 5L;
        String newCustomerName = "name";
        Customer customer = Customer.of(newCustomerName);

        sut.insertCustomer(customer);
        Customer actual = sut.selectCustomerById(newCustomerId).get();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", newCustomerId)
                .hasFieldOrPropertyWithValue("name", newCustomerName);
    }

    @Test
    @DisplayName("고객 추가 시, 이름 누락")
    void insert_customer_no_name() {
        Customer customer = Customer.of(null);

        assertThatThrownBy(() -> sut.insertCustomer(customer))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("고객 id를 통해서 검색")
    void select_customer_by_id() {
        long id = 1L;
        String name = "customer1";

        Customer actual = sut.selectCustomerById(1L).get();

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", name);
    }

    @Test
    @DisplayName("고객 id로 검색 시, 해당 id에 맞는 고객 존재하지 않음")
    void select_customer_by_id_fail() {
        long id = 100L;

        assertThatThrownBy(() -> sut.selectCustomerById(id)).isInstanceOf(NoResultException.class);
    }

}

class TestJpaConfig {
    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepositoryImpl();
    }
}
package com.cafe.coffeeOrder.customer;

import com.cafe.coffeeOrder.customer.dto.RequestCreateCustomer;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomerItem;
import com.cafe.coffeeOrder.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/testData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CustomerTest {

    @Autowired
    CustomerService sut;

    @Test
    @DisplayName("고객 조회")
    void get_customer_by_id() {
        ResponseCustomerItem actual = sut.getCustomer(1L);

        assertThat(actual).hasFieldOrPropertyWithValue("name", "customer1");
    }

    @Test
    @DisplayName("고객 생성")
    void create_customer() {
        String name = "test";
        RequestCreateCustomer request = RequestCreateCustomer.builder().name(name).build();

        ResponseCustomerItem actual = sut.createCustomer(request);

        assertThat(actual).hasFieldOrPropertyWithValue("name", name);
    }


}

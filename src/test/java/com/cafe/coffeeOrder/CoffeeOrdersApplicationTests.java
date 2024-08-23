package com.cafe.coffeeOrder;

import com.cafe.coffeeOrder.customer.dto.RequestCreateCustomer;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomerItem;
import com.cafe.coffeeOrder.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CoffeeOrdersApplicationTests {

    @Autowired
    CustomerService customerService;


    @Test
    @Rollback(value = false)
    @DisplayName("고객 생성")
    void create_customer() {
        String name = "test";
        RequestCreateCustomer request = RequestCreateCustomer.builder().name(name).build();

        ResponseCustomerItem actual = customerService.createCustomer(request);

        assertThat(actual).hasFieldOrPropertyWithValue("name", name);
    }


}

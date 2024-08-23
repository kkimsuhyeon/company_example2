package com.cafe.coffeeOrder.customer.dto;

import com.cafe.coffeeOrder.customer.domain.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseCustomer {

    private long id;
    private String name;

    public static ResponseCustomer from(Customer entity) {
        return new ResponseCustomer(entity.getId(), entity.getName());
    }
}
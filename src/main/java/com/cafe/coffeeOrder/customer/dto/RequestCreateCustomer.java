package com.cafe.coffeeOrder.customer.dto;

import com.cafe.coffeeOrder.customer.domain.Customer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestCreateCustomer {
    private String name;

    public Customer toEntity() {
        return Customer.of(this.name);
    }
}

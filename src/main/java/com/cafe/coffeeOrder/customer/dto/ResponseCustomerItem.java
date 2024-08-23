package com.cafe.coffeeOrder.customer.dto;

import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.orders.dto.ResponseOrders;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseCustomerItem {

    private long id;

    private String name;

    private List<ResponseOrders> orders;

    public static ResponseCustomerItem fromEntity(Customer entity) {
        return new ResponseCustomerItem(
                entity.getId(),
                entity.getName(),
                entity.getOrders().stream().map(ResponseOrders::fromEntity).toList()
        );
    }

}

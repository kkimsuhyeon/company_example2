package com.cafe.coffeeOrder.orders.dto;


import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseOrders {

    private long id;

    private OrdersStatus status;

    public static ResponseOrders fromEntity(Orders entity) {
        return new ResponseOrders(entity.getId(), entity.getStatus());
    }

}

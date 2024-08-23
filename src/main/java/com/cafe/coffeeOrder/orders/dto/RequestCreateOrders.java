package com.cafe.coffeeOrder.orders.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestCreateOrders {

    private long customerId;

    private long beverageId;

}
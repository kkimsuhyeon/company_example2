package com.cafe.coffeeOrder.orders.service;

import com.cafe.coffeeOrder.orders.dto.RequestCreateOrders;
import com.cafe.coffeeOrder.orders.dto.ResponseOrdersItem;

public interface OrdersService {

    public ResponseOrdersItem getOrderItem(long id);

    public ResponseOrdersItem createOrder(RequestCreateOrders request);

    public ResponseOrdersItem sendBeverage(long id);

}
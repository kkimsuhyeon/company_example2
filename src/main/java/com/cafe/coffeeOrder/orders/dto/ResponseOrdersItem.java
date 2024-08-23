package com.cafe.coffeeOrder.orders.dto;

import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomer;
import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.payment.dto.ResponsePayment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResponseOrdersItem {

    private Long id;

    private OrdersStatus status;

    private ResponseCustomer customer;

    private ResponseBeverageItem beverage;

    private Set<ResponsePayment> payments;

    public static ResponseOrdersItem from(Orders entity) {
        if (entity.getPayments() == null) {
            return new ResponseOrdersItem(
                    entity.getId(),
                    entity.getStatus(),
                    ResponseCustomer.from(entity.getCustomer()),
                    ResponseBeverageItem.from(entity.getBeverage()),
                    null);
        } else {
            return new ResponseOrdersItem(
                    entity.getId(),
                    entity.getStatus(),
                    ResponseCustomer.from(entity.getCustomer()),
                    ResponseBeverageItem.from(entity.getBeverage()),
                    entity.getPayments().stream().map(ResponsePayment::from).collect(Collectors.toSet()));
        }
    }

}
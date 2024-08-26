package com.cafe.coffeeOrder.orders.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.exception.BeverageException;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.exception.CustomerException;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
import com.cafe.coffeeOrder.common.exception.CustomException;
import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.orders.dto.RequestCreateOrders;
import com.cafe.coffeeOrder.orders.dto.ResponseOrdersItem;
import com.cafe.coffeeOrder.orders.exception.OrdersException;
import com.cafe.coffeeOrder.orders.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BeverageRepository beverageRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository orderRepository, CustomerRepository customerRepository, BeverageRepository beverageRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.beverageRepository = beverageRepository;
    }

    @Override
    public ResponseOrdersItem getOrderItem(long id) {
        return orderRepository.selectOrderById(id)
                .map(ResponseOrdersItem::from)
                .orElseThrow(() -> new CustomException(OrdersException.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseOrdersItem createOrder(RequestCreateOrders request) {
        Customer customer = customerRepository.selectCustomerById(request.getCustomerId())
                .orElseThrow(() -> new CustomException(CustomerException.NOT_FOUND));

        Beverage beverage = beverageRepository.selectBeverageById(request.getBeverageId())
                .orElseThrow(() -> new CustomException(BeverageException.NOT_FOUND));

        Orders newOrders = Orders.of(customer, beverage);
        orderRepository.insertOrders(newOrders);

        return ResponseOrdersItem.from(newOrders);
    }

    @Override
    public ResponseOrdersItem sendBeverage(long id) {
        return orderRepository.selectOrderById(id).map((item) -> {
            item.setStatus(OrdersStatus.DELIVERY_COMPLETE);
            return ResponseOrdersItem.from(item);
        }).orElseThrow(() -> new CustomException(OrdersException.NOT_FOUND));
    }
}
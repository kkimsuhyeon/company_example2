package com.cafe.coffeeOrder.orders.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
import com.cafe.coffeeOrder.orders.domain.Orders;
import com.cafe.coffeeOrder.orders.domain.constant.OrdersStatus;
import com.cafe.coffeeOrder.orders.dto.RequestCreateOrders;
import com.cafe.coffeeOrder.orders.dto.ResponseOrdersItem;
import com.cafe.coffeeOrder.orders.repository.OrdersRepository;
import com.cafe.coffeeOrder.payment.repository.PaymentRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 order 없음"));
    }

    @Override
    @Transactional
    public ResponseOrdersItem createOrder(RequestCreateOrders request) {
        Customer customer = customerRepository.selectCustomerById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 유저가 없음"));

        Beverage beverage = beverageRepository.selectBeverageById(request.getBeverageId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 음료가 없음"));

        Orders newOrders = Orders.of(customer, beverage);
        orderRepository.insertOrders(newOrders);

        return ResponseOrdersItem.from(newOrders);
    }

    @Override
    public ResponseOrdersItem sendBeverage(long id) {
        return orderRepository.selectOrderById(id).map((item) -> {
            item.setStatus(OrdersStatus.DELIVERY_COMPLETE);
            return ResponseOrdersItem.from(item);
        }).orElseThrow(() -> new IllegalArgumentException("해당 주문 존재하지 않음"));
    }
}
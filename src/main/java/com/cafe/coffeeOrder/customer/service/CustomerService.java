package com.cafe.coffeeOrder.customer.service;

import com.cafe.coffeeOrder.customer.dto.RequestCreateCustomer;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomerItem;

public interface CustomerService {

    public ResponseCustomerItem getCustomer(long id);

    public ResponseCustomerItem createCustomer(RequestCreateCustomer request);

}
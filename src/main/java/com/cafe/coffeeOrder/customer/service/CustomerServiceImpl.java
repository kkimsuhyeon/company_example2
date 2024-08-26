package com.cafe.coffeeOrder.customer.service;

import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.dto.RequestCreateCustomer;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomerItem;
import com.cafe.coffeeOrder.customer.exception.CustomerException;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
import com.cafe.coffeeOrder.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseCustomerItem getCustomer(long id) {
        return customerRepository.selectCustomerById(id)
                .map(ResponseCustomerItem::fromEntity)
                .orElseThrow(() -> new CustomException(CustomerException.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseCustomerItem createCustomer(RequestCreateCustomer request) {
        Customer customer = customerRepository.insertCustomer(request.toEntity());
        return ResponseCustomerItem.fromEntity(customer);
    }
}
package com.cafe.coffeeOrder.customer.service;

import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.dto.RequestCreateCustomer;
import com.cafe.coffeeOrder.customer.dto.ResponseCustomerItem;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 존재하지 않음"));

    }

    @Override
    @Transactional
    public ResponseCustomerItem createCustomer(RequestCreateCustomer request) {
        Customer customer = customerRepository.insertCustomer(request.toEntity());
        return ResponseCustomerItem.fromEntity(customer);
    }
}
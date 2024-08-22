package com.cafe.coffeeOrder.customer.repository;

import com.cafe.coffeeOrder.customer.domain.Customer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomerRepository {

    public Customer insertCustomer(Customer customer);

    public Optional<Customer> selectCustomerById(Long id);

}
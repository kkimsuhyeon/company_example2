package com.cafe.coffeeOrder.customer.repository;

import com.cafe.coffeeOrder.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Customer insertCustomer(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        String query = "SELECT c FROM Customer AS c" +
                " WHERE c.id = :id";

        Customer result = entityManager.createQuery(query, Customer.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}

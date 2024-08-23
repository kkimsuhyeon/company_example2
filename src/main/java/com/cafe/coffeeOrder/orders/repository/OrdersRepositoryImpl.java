package com.cafe.coffeeOrder.orders.repository;

import com.cafe.coffeeOrder.orders.domain.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdersRepositoryImpl implements OrdersRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Orders> selectOrders() {
        String query = "SELECT o FROM Orders AS o" +
                " INNER JOIN FETCH o.customer AS c" +
                " INNER JOIN FETCH o.beverage AS b";

        return entityManager.createQuery(query, Orders.class).getResultList();
    }

    @Override
    public Optional<Orders> selectOrderById(long id) {
        String query = "SELECT o FROM Orders AS o" +
                " INNER JOIN FETCH o.customer AS c" +
                " INNER JOIN FETCH o.beverage AS b" +
                " LEFT JOIN FETCH o.payments AS p" +
                " WHERE o.id = :id";

        Orders orders = entityManager
                .createQuery(query, Orders.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(orders);
    }

    @Override
    public Orders insertOrders(Orders orders) {
        entityManager.persist(orders);
        return orders;
    }
}
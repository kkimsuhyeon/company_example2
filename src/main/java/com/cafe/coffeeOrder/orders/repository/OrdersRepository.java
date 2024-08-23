package com.cafe.coffeeOrder.orders.repository;

import com.cafe.coffeeOrder.orders.domain.Orders;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface OrdersRepository {

    public List<Orders> selectOrders();

    public Optional<Orders> selectOrderById(long id);

    public Orders insertOrders(Orders orders);

}
package com.glotov.myprojectsuper.service;

import com.glotov.myprojectsuper.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    List<Order> findOrdersByCustomerId(int customerId);

    Order addOrder(Order order);

    Order editOrder(int orderId, Order order);
    Order getOrderById(int orderId);

    void deleteOrder(int orderId);

    void payOrder(int orderId);
}

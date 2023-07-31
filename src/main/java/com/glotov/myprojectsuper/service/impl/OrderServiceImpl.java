package com.glotov.myprojectsuper.service.impl;

import com.glotov.myprojectsuper.model.Order;
import com.glotov.myprojectsuper.repository.OrderRepository;
import com.glotov.myprojectsuper.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomer_Id(customerId);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order editOrder(int orderId, Order order) {
        Optional<Order> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Заказ с указанным ID не найден.");
        }

        Order existingOrder = existingOrderOpt.get();
        if (existingOrder.getCustomer().getId() != order.getCustomer().getId()) {
            throw new IllegalArgumentException("Заказ с указанным ID не принадлежит текущему клиенту.");
        }

        existingOrder.setMenuItem(order.getMenuItem());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setCancelled(order.isCancelled());
        existingOrder.setReviewed(order.isReviewed());
        existingOrder.setRating(order.getRating());
        existingOrder.setPaid(order.isPaid());
        existingOrder.setOrderTime(order.getOrderTime());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(int orderId) {
        Optional<Order> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Заказ с указанным ID не найден.");
        }

        Order existingOrder = existingOrderOpt.get();
        orderRepository.delete(existingOrder);
    }
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElse(null);
    }
    public void payOrder(int orderId) {
        Optional<Order> existingOrderOpt = orderRepository.findById(orderId);
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Заказ с указанным ID не найден.");
        }

        Order existingOrder = existingOrderOpt.get();
        existingOrder.setPaid(true);
        orderRepository.save(existingOrder);
    }
}

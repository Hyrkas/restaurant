package com.aikka.restaurant.service;

import com.aikka.restaurant.dao.OrderDAO;
import com.aikka.restaurant.model.Order;
import com.aikka.restaurant.model.dto.OrderCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class OrderService {

    @Autowired
    OrderDAO orderDAO;

    public List<Order> fetchHistory(Integer customerId) {
        return orderDAO.findLastFiveOrders(customerId);
    }

    @Transactional
    public Order createOrder(OrderCreateDTO orderDTO, Integer customerId) {
        if (orderDTO == null) throw new RuntimeException("order payload required");
        if (orderDTO.getRestaurantId() == null) throw new RuntimeException("restaurant id is mandatory");
        if (orderDTO.getItems().isEmpty()) throw new RuntimeException("order items are required");

        return orderDAO.insertOrder(orderDTO, customerId);
    }

    public Order fetchOrderById(Integer orderId) {
        return orderDAO.findOrderByOrderId(orderId);
    }

    @Transactional
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }
}

package com.aikka.restaurant.model.dto;

import com.aikka.restaurant.model.Order;

public class OrderStatusDTO {

    private Order.OrderStatus status;

    public OrderStatusDTO() {
    }

    public Order.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
}

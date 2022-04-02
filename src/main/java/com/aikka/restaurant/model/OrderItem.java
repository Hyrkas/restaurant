package com.aikka.restaurant.model;

import java.util.Objects;

public class OrderItem {

    private Integer orderId;
    private Integer menuItemId;

    public OrderItem() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return getOrderId().equals(orderItem.getOrderId()) && getMenuItemId().equals(orderItem.getMenuItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getMenuItemId());
    }
}

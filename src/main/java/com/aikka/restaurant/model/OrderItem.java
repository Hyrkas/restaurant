package com.aikka.restaurant.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "customer_order_item")
public class OrderItem implements Serializable {

    @Id
    private Integer orderId;
    @Id
    private Integer menuItemId;

    public OrderItem() {
    }

    public OrderItem(Integer orderId, Integer itemId) {
        this.orderId = orderId;
        this.menuItemId = itemId;
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

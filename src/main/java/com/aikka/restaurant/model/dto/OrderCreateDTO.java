package com.aikka.restaurant.model.dto;

import java.util.HashSet;
import java.util.Set;

public class OrderCreateDTO {
    private Integer restaurantId;
    private Set<Integer> items = new HashSet<>();

    public OrderCreateDTO() {
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Set<Integer> getItems() {
        return items;
    }

    public void setItems(Set<Integer> items) {
        this.items = items;
    }
}

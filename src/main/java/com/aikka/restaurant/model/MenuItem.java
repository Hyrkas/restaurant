package com.aikka.restaurant.model;

import java.util.Objects;

public class MenuItem {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer restaurantId;

    public MenuItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return getId().equals(menuItem.getId()) && getRestaurantId().equals(menuItem.getRestaurantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRestaurantId());
    }
}

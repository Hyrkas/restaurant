package com.aikka.restaurant.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "menu_item")
public class MenuItem {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer restaurantId;
    private Restaurant restaurant;

    public MenuItem() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "restaurant_id")
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

    @Transient
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

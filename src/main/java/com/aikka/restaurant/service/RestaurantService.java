package com.aikka.restaurant.service;

import com.aikka.restaurant.dao.RestaurantDAO;
import com.aikka.restaurant.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RestaurantService {
    @Autowired
    RestaurantDAO restaurantDAO;

    public Collection<Restaurant> fetchRestaurants() {
        return restaurantDAO.findRestaurants();
    }

    public Restaurant fetchRestaurant(Integer restaurantId) {
        return restaurantDAO.findRestaurant(restaurantId);
    }
}

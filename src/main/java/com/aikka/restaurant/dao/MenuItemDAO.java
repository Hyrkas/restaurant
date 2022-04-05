package com.aikka.restaurant.dao;

import com.aikka.restaurant.model.MenuItem;
import com.aikka.restaurant.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MenuItemDAO {

    @Autowired
    EntityManager em;

    public List<MenuItem> findItems() {
        List<MenuItem> items = em.createNativeQuery("select * from menu_item", MenuItem.class).getResultList();
        List<Restaurant> restaurants = em.createNativeQuery("select * from restaurant", Restaurant.class)
                .getResultList();
        Map<Integer, Restaurant> restMap = restaurants.stream()
                .collect(Collectors.toMap(Restaurant::getId, Function.identity()));
        items.forEach(item -> {
            Restaurant restaurant = restMap.get(item.getRestaurantId());
            item.setRestaurant(restaurant);
        });
        return items;
    }
}

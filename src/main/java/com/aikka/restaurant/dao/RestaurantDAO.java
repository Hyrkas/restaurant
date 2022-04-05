package com.aikka.restaurant.dao;

import com.aikka.restaurant.model.MenuItem;
import com.aikka.restaurant.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Component
public class RestaurantDAO {
    @Autowired
    EntityManager em;

    public Collection<Restaurant> findRestaurants() {
        return em.createNativeQuery("select * from restaurant", Restaurant.class).getResultList();
    }

    public Restaurant findRestaurant(Integer restaurantId) {
        try {
            Restaurant restaurant = (Restaurant) em.createNativeQuery("select * from restaurant where id = :id", Restaurant.class)
                    .setParameter("id", restaurantId)
                    .getSingleResult();

            List<MenuItem> items = em.createNativeQuery("select * from menu_item where restaurant_id = :id", MenuItem.class)
                    .setParameter("id", restaurantId)
                    .getResultList();
            restaurant.setMenuItems(items);
            return restaurant;

        } catch (NoResultException e) {
            throw new RuntimeException("no restaurant with provided id found");
        }
    }
}

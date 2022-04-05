package com.aikka.restaurant.rest;

import com.aikka.restaurant.model.Restaurant;
import com.aikka.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Restaurants", description = "api for fetching restaurants")
@RestController
@RequestMapping(path = "restaurants/", produces = "application/json", consumes = "application/json")
public class RestaurantResource {
    @Autowired
    RestaurantService restaurantService;
    @GetMapping
    @Parameters(value = {
            @Parameter(
                    description = "content type header",
                    name = "Content-Type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public Collection<Restaurant> getRestaurants() {
        return restaurantService.fetchRestaurants();
    }

    @GetMapping(path = "{id}")
    @Parameters(value = {
            @Parameter(
                    description = "content type header",
                    name = "Content-Type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public Restaurant getRestaurant(@PathVariable("id") Integer id) {
        return restaurantService.fetchRestaurant(id);
    }
}

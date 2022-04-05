package com.aikka.restaurant.rest;

import com.aikka.restaurant.model.MenuItem;
import com.aikka.restaurant.service.MenuItemService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Menu Items", description = "api for fetching restaurants menu items")
@RestController
@RequestMapping(path = "menu-items/", produces = "application/json", consumes = "application/json")
public class MenuResource {

    @Autowired
    MenuItemService menuItemService;
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
    public Collection<MenuItem> getMenuItems() {
        return menuItemService.fetchItems();
    }
}

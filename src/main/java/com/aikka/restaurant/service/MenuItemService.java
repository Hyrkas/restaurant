package com.aikka.restaurant.service;

import com.aikka.restaurant.dao.MenuItemDAO;
import com.aikka.restaurant.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuItemService {
    @Autowired
    MenuItemDAO menuItemDAO;

    public List<MenuItem> fetchItems() {
        return menuItemDAO.findItems();
    }
}

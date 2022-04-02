package com.aikka.restaurant.service;

import com.aikka.restaurant.dao.UserDAO;
import com.aikka.restaurant.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User createUser(User user) {
        return userDAO.insertUser(user);
    }

    public User fetchProfile() {
        return userDAO.findProfile();
    }

    public User fetchUserByUserNameAndPassword(String userName, String password) {
        return userDAO.findUserByUserNameAndPassword(userName, password);
    }
}

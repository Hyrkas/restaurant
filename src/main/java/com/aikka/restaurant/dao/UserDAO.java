package com.aikka.restaurant.dao;

import com.aikka.restaurant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    EntityManager em;

    @Transactional
    public User insertUser(User user) {
        em.persist(user);
        logger.info("User created: {}", user);
        return user;
    }

    public User findProfile() {
        logger.info("searching profile");
        return em.find(User.class, 1);
    }

    public User findUserByUserNameAndPassword(String userName, String password) {
        try {
            return (User) em.createNativeQuery(
                    "select * from user " +
                            "where username like :username and password like :password", User.class)
                    .setParameter("username", userName)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("User tried to login with non valid credentials");
            return null;
        }
    }
}

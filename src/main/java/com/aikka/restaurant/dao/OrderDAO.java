package com.aikka.restaurant.dao;

import com.aikka.restaurant.model.MenuItem;
import com.aikka.restaurant.model.Order;
import com.aikka.restaurant.model.OrderItem;
import com.aikka.restaurant.model.dto.OrderCreateDTO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderDAO {

    @Autowired
    EntityManager em;

    public List<Order> findLastFiveOrders(Integer customerUserId) {

        List<Order> orders = em.createNativeQuery(
                "select * from customer_order where customer_id = :customerId order by created_at desc limit 5", Order.class
        ).setParameter("customerId", customerUserId)
                .getResultList();
        Map<Integer, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getId, Function.identity()));

        List<Object[]> rows = em.unwrap(Session.class).createNativeQuery(
                        "select {mi.*}, {oi.*} from menu_item mi left join customer_order_item oi on oi.menu_item_id = mi.id where oi.order_id in :ids"
                ).setParameter("ids", orderMap.keySet())
                .addEntity("mi", MenuItem.class)
                .addEntity("oi", OrderItem.class)
                .getResultList();

        // do mapping
        for (Object[] row : rows) {
            MenuItem menuItem = null;
            OrderItem orderItem = null;
            for (Object o : row) {
                if (o instanceof OrderItem oi) {
                    orderItem = oi;
                }
                if (o instanceof MenuItem mi) {
                    menuItem = mi;
                }
            }
            if (orderItem != null)
                orderMap.get(orderItem.getOrderId()).addItem(menuItem);
        }
        return orders;
    }

    public Order findOrderByCustomerIdAndOrderId(Integer customerUserId, Integer orderId) {
        try {
            Order order = (Order) em.createNativeQuery(
                            "select * from customer_order where customer_id = :customerId and id =:orderId ", Order.class
                    ).setParameter("customerId", customerUserId)
                    .setParameter("orderId", orderId)
                    .getSingleResult();

            List<Object[]> rows = em.unwrap(Session.class).createNativeQuery(
                            "select {mi.*}, {oi.*} from menu_item mi left join customer_order_item oi on oi.menu_item_id = mi.id where oi.order_id = :orderId"
                    ).setParameter("orderId", orderId)
                    .addEntity("mi", MenuItem.class)
                    .addEntity("oi", OrderItem.class)
                    .getResultList();

            // do mapping
            for (Object[] row : rows) {
                MenuItem menuItem = null;
                OrderItem orderItem = null;
                for (Object o : row) {
                    if (o instanceof OrderItem oi) {
                        orderItem = oi;
                    }
                    if (o instanceof MenuItem mi) {
                        menuItem = mi;
                    }
                }
                if (orderItem != null) order.addItem(menuItem);
            }
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    public Order findOrderByOrderId(Integer orderId) {
        try {
            Order order = (Order) em.createNativeQuery(
                            "select * from customer_order where id =:orderId ", Order.class
                    )
                    .setParameter("orderId", orderId)
                    .getSingleResult();

            List<Object[]> rows = em.unwrap(Session.class).createNativeQuery(
                            "select {mi.*}, {oi.*} from menu_item mi left join customer_order_item oi on oi.menu_item_id = mi.id where oi.order_id = :orderId"
                    ).setParameter("orderId", orderId)
                    .addEntity("mi", MenuItem.class)
                    .addEntity("oi", OrderItem.class)
                    .getResultList();

            // do mapping
            for (Object[] row : rows) {
                MenuItem menuItem = null;
                OrderItem orderItem = null;
                for (Object o : row) {
                    if (o instanceof OrderItem oi) {
                        orderItem = oi;
                    }
                    if (o instanceof MenuItem mi) {
                        menuItem = mi;
                    }
                }
                if (orderItem != null) order.addItem(menuItem);
            }
            return order;
        } catch (NoResultException e) {
            return null;
        }
    }
    public Order insertOrder(OrderCreateDTO orderDTO, Integer customerId) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setRestaurantId(orderDTO.getRestaurantId());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCustomerId(customerId);
        em.persist(order);
        List<OrderItem> items = new ArrayList<>();
        for (Integer item : orderDTO.getItems()) {
            items.add(new OrderItem(order.getId(), item));
        }
        items.forEach(em::persist);
        return findOrderByCustomerIdAndOrderId(customerId, order.getId());
    }

    public void updateOrder(Order order) {
        em.merge(order);
    }
}

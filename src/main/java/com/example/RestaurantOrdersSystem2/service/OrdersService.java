package com.example.RestaurantOrdersSystem2.service;

import com.example.RestaurantOrdersSystem2.model.Order;
import com.example.RestaurantOrdersSystem2.model.OrderRepository;
import com.example.RestaurantOrdersSystem2.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public void prepopulateOrdersForCurrentUser() {
        // Get the current user's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = userRepository.findByUsername(authentication.getName()).getId();

        // Create some sample orders for the current user
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setUser(userRepository.findById(currentUserId).get());
        order1.setOrderDate(new Date());
        order1.setTotalPrice(100.0);
        orders.add(order1);

        Order order2 = new Order();
        order2.setUser(userRepository.findById(currentUserId).get());
        order2.setOrderDate(new Date());
        order2.setTotalPrice(200.0);
        orders.add(order2);

        orderRepository.saveAll(orders);
    }

    public List<Order> findByUserId(Long id) {
        return orderRepository.findByUserId(id);
    }

    public Order save(Order order) {
        orderRepository.save(order);
        return order;
    }
}

package com.example.RestaurantOrdersSystem2.service;

import com.example.RestaurantOrdersSystem2.model.Order;
import com.example.RestaurantOrdersSystem2.model.User;
import com.example.RestaurantOrdersSystem2.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createDefaultUser() {
        if (getAllUsers().isEmpty()) {
            User user = User.builder().id(1L).email("alexandru.uioreanu@gmail.com").password("test").username("Alex").build();

            List<Order> orders = new ArrayList<>();
            Order order1 = Order.builder().user1(user).description("French fries with eqqs cost:10$").build();
            Order order2 = Order.builder().user1(user).description("Spaghetti with meat cost:20$").build();
            Order order3 = Order.builder().user1(user).description("Argentinean Beef with fries cost:100$").build();

            orders.add(order1);
            orders.add(order2);
            orders.add(order3);

            user.setOrders(orders);

            save(user);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

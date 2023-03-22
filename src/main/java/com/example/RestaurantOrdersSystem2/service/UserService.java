package com.example.RestaurantOrdersSystem2.service;

import com.example.RestaurantOrdersSystem2.model.User;
import com.example.RestaurantOrdersSystem2.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}

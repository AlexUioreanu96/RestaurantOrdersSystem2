package com.example.RestaurantOrdersSystem2.api;

import com.example.RestaurantOrdersSystem2.model.Order;
import com.example.RestaurantOrdersSystem2.model.OrderRepository;
import com.example.RestaurantOrdersSystem2.model.User;
import com.example.RestaurantOrdersSystem2.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        order.setUser(user);
        orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderId)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            user.removeOrder(order);
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody Order updatedOrder, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderId)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setPrice(updatedOrder.getPrice());
            order.setDescription(updatedOrder.getDescription());
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(savedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

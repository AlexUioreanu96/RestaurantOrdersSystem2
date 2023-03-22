package com.example.RestaurantOrdersSystem2.api;

import com.example.RestaurantOrdersSystem2.model.Order;
import com.example.RestaurantOrdersSystem2.model.User;
import com.example.RestaurantOrdersSystem2.service.OrdersService;
import com.example.RestaurantOrdersSystem2.service.UserService;
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
    private OrdersService ordersService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Order> orders = ordersService.findByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        order.setUser1(user);
        ordersService.save(order);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderId)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            user.removeOrder(order);
            userService.save(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody Order updatedOrder, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Optional<Order> orderOptional = user.getOrders().stream().filter(order -> order.getId().equals(orderId)).findFirst();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setDescription(updatedOrder.getDescription());
            Order savedOrder = ordersService.save(order);
            return ResponseEntity.ok(savedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

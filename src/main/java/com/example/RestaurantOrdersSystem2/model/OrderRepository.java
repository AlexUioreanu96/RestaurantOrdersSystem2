package com.example.RestaurantOrdersSystem2.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser1Id(Long userId);
}

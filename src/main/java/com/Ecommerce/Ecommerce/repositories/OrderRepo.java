package com.Ecommerce.Ecommerce.repositories;

import com.Ecommerce.Ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}

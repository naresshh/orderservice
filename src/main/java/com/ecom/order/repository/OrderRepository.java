package com.ecom.order.repository;

import com.ecom.order.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
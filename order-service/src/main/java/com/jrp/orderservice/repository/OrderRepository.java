package com.jrp.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrp.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

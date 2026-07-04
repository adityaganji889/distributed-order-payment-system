package com.app.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.orderservice.entities.OrderDO;

@Repository
public interface OrderRepository extends JpaRepository<OrderDO,Long> {

	public OrderDO findByOrderId(String orderId);
}

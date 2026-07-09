package com.app.paymentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.paymentservice.entities.PaymentDO;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDO,Long> {
	
	public PaymentDO findByOrderId(String orderId);
	
}

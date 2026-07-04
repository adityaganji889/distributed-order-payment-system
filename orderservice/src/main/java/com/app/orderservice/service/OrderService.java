package com.app.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.orderservice.dto.Order;
import com.app.orderservice.entities.OrderDO;
import com.app.orderservice.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OrderRepository repo;

	@KafkaListener(
			topics = "${app.kafka.topic}",
			groupId = "${spring.kafka.consumer.group-id}")
	public void consumeOrder(String payment) {
		System.out.println("Received payment: " + payment);
		String[] arr = payment.split(",");
		OrderDO fetchedOrderDo = repo.findByOrderId(arr[0]);
		System.out.println(fetchedOrderDo);
		fetchedOrderDo.setStatus(arr[1]);
		repo.save(fetchedOrderDo);
	}
	
	public String orderProcessing(Order order) {
		OrderDO orderDo = new OrderDO();
		orderDo.setOrderId(order.getOrderId());
		orderDo.setOrderName(order.getOrderName());
		orderDo.setItemName(order.getItemName());
		orderDo.setItemType(order.getItemType());
		orderDo.setStatus("success");
		repo.save(orderDo);
		System.out.println("Order saved successfully.");
		String url = "http://localhost:8083/payments/process?payment="+order.toString();
		restTemplate.getForObject(url, String.class);
		System.out.println("Payment service called successfully");
		return "order processed successful.";
	}
}

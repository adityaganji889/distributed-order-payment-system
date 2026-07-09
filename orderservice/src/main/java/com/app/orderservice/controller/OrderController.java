package com.app.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.orderservice.dto.Order;
import com.app.orderservice.exceptionhandlers.KafkaConsumerCustomException;
import com.app.orderservice.service.OrderService;

import feign.FeignException;
import feign.RetryableException;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@PostMapping("/process")
	public String processOrder(@RequestBody Order order) throws FeignException,RetryableException,TransactionSystemException, KafkaConsumerCustomException{
		service.orderProcessing(order);
		return order.getItemName() + " processed succesfully.";
	}
}

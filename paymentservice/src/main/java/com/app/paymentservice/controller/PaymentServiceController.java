package com.app.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.paymentservice.dtos.Payment;
import com.app.paymentservice.exceptionhandlers.KafkaProducerCustomException;
import com.app.paymentservice.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/payments")
public class PaymentServiceController {

	@Autowired
	private PaymentService service;
	
//	@Value("${app.kafka.topic}")
//	private String topic;
	
	@Value("${app.kafka.order-created.topic}")
	private String orderCreatedTopic;
	
//	@GetMapping("/process")
	@PostMapping("/process")
//	public String processPayment(@RequestParam("payment") String payment) {
	public String processPayment(@RequestBody Payment payment) throws JsonProcessingException, KafkaProducerCustomException, TransactionSystemException {
		service.processTransaction(orderCreatedTopic,payment);	
		return "Payment Processed Successfully";
	}
}

package com.app.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentServiceController {

	@Autowired
	private PaymentService service;
	
	@Value("${app.kafka.topic}")
	private String topic;
	
	@GetMapping("/process")
	public String processPayment(@RequestParam("payment") String payment) {
		service.processTransaction(topic,payment);
		return "Payment Processed Successfully";
	}
}

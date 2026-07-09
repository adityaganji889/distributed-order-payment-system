package com.app.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.paymentservice.dtos.Payment;
import com.app.paymentservice.service.PaymentService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/payments")
public class PaymentServiceController {

	@Autowired
	private PaymentService service;
	
	@Value("${app.kafka.topic}")
	private String topic;
	
//	@GetMapping("/process")
	@PostMapping("/process")
	@Transactional
//	public String processPayment(@RequestParam("payment") String payment) {
	public String processPayment(@RequestBody Payment payment) {
		try {
			service.processTransaction(topic,payment);	
		}
		catch(Exception e) {
			System.out.println("Exception occurred:"+e.getMessage());
			return "Error occurred in Payment";
		}
		return "Payment Processed Successfully";
	}
}

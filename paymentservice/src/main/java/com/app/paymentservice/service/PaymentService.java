package com.app.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${app.kafka.topic}")
	private String tempTopic;

	public void processTransaction(String topic, String payment) {
		kafkaTemplate.send(topic, payment);
		System.out.println("Payment sent: " + payment);
	}

//	@Async
	@Scheduled(fixedRate = 120000)
	public void processPaymentAsync() {

		try {

			// Simulate payment gateway processing

//			Thread.sleep(120000); // 2 minutes

			boolean paymentSuccess = false;

			String orderId = "2";
			String message = "";

			if (paymentSuccess) {

				message = "SUCCESS";

			} else {

				message = "FAILED";

			}

			kafkaTemplate.send(tempTopic, orderId+","+message);

			System.out.println("Kafka event sent");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}

package com.app.paymentservice.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.paymentservice.dtos.Payment;
import com.app.paymentservice.dtos.Payment;
import com.app.paymentservice.entities.PaymentDO;
import com.app.paymentservice.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

@Service
public class PaymentService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private PaymentRepository repo;
	
	@Autowired
	private Random random;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("${app.kafka.topic}")
	private String tempTopic;
	

	public void processTransaction(String topic, Payment payment) throws JsonProcessingException {
		String requestJSON = mapper.writeValueAsString(payment); //Converted Payment DTO to json request string
		kafkaTemplate.send(topic, requestJSON);
		PaymentDO paymentDo = new PaymentDO();
		paymentDo.setOrderId(null);
		paymentDo.setPaymentAmount(null);
		paymentDo.setStatus("Pending");
		repo.save(paymentDo);
		System.out.println("Payment sent: " + payment);
	}

	@Scheduled(fixedRate = 120000)
	public void processPaymentAsync() {

		try {

			// Simulate payment gateway processing

			boolean paymentSuccess = random.nextBoolean();

			String orderId = String.valueOf(random.nextInt(1,10));
			
			String message = "";

			if (paymentSuccess) {
				message = "Success";
			} else {
				message = "Failed";
			}

			//Update payment status in PaymentDO table changes starts
			PaymentDO payment = repo.findByOrderId(orderId);
			
			payment.setStatus(message);
			
			repo.save(payment);
			//Update payment status in PaymentDO table changes ends
			
//			kafkaTemplate.send(tempTopic, orderId+","+message);
			
			//Sending JSON string as response changes starts
			Payment paymentResponse = new Payment();
			paymentResponse.setOrderId(orderId);
			paymentResponse.setPaymentAmount(payment.getPaymentAmount());
			paymentResponse.setStatus(message);
			
			String responseJSON = mapper.writeValueAsString(paymentResponse);
			
			kafkaTemplate.send(tempTopic, responseJSON);
			//Sending JSON string as response changes ends

			System.out.println("Kafka event sent");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}

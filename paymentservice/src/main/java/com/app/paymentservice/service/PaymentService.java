package com.app.paymentservice.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.app.paymentservice.dtos.Payment;
import com.app.paymentservice.entities.PaymentDO;
import com.app.paymentservice.exceptionhandlers.KafkaProducerCustomException;
import com.app.paymentservice.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Value("${app.kafka.order-updated.topic}")
	private String orderUpdatedTopic;

	@Transactional
	public void processTransaction(String topic, Payment payment) throws JsonProcessingException, TransactionSystemException {
		try {
			String requestJSON = mapper.writeValueAsString(payment); // Converted Payment DTO to json request string
			kafkaTemplate.send(topic, requestJSON);
			PaymentDO paymentDo = new PaymentDO();
			paymentDo.setOrderId(payment.getOrderId());
			paymentDo.setPaymentAmount(payment.getPaymentAmount());
			paymentDo.setStatus("Pending");
			repo.save(paymentDo);
			System.out.println("Payment sent: " + payment);
		} catch (KafkaProducerException ex) {
			System.out.println("Failed to send message" + ex.getMessage());
			throw new KafkaProducerCustomException("Unable to publish message due to reason:" + ex.getMessage());
		}
	}

//	@Scheduled(fixedRate = 120000) //Every 2 mins
	@Scheduled(fixedRate = 10000) // Every 10 s
	public void processPaymentAsync() throws JsonProcessingException {

		try {

			// Simulate payment gateway processing

			boolean paymentSuccess = random.nextBoolean();

			String orderId = String.valueOf(random.nextInt(1, 10));

			System.out.println("orderId:" + orderId);

			String message = "";

			if (paymentSuccess) {
				message = "Success";
			} else {
				message = "Failed";
			}

			// Update payment status in PaymentDO table changes starts
			Optional<PaymentDO> payment = repo.findByOrderId(orderId);

			if (payment.isPresent()) {
				PaymentDO existingPayment = payment.get();
				if (existingPayment.getStatus().equals("Failed") || existingPayment.getStatus().equals("Pending")) {
					existingPayment.setStatus(message);
					repo.save(existingPayment);
					// Sending JSON string as response changes starts
					Payment paymentResponse = new Payment();
					paymentResponse.setOrderId(orderId);
					paymentResponse.setPaymentAmount(existingPayment.getPaymentAmount());
					paymentResponse.setStatus(message);

					String responseJSON = mapper.writeValueAsString(paymentResponse);

//					kafkaTemplate.send(tempTopic, responseJSON);
					kafkaTemplate.send(orderUpdatedTopic, responseJSON);
					System.out.println("Kafka event:order updated sent");
					// Sending JSON string as response changes ends
				}
			}
			// Update payment status in PaymentDO table changes ends

//			kafkaTemplate.send(tempTopic, orderId+","+message);
			System.out.println(
					"Kafka event:order updated not sent as either order is not found or it's payment is successfully completed");

		} catch (KafkaProducerException ex) {
    	    System.out.println("Failed to send message"+ ex.getMessage());
    	    throw new KafkaProducerCustomException("Unable to publish message due to reason:"+ex.getMessage());
        }

	}
}

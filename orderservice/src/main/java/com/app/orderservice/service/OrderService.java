package com.app.orderservice.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.orderservice.dto.Order;
import com.app.orderservice.dto.Payment;
import com.app.orderservice.entities.OrderDO;
import com.app.orderservice.feignclients.PaymentFeignClient;
import com.app.orderservice.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

//	@Autowired
//	private RestTemplate restTemplate;

	@Autowired
	private OrderRepository repo;
	
	@Autowired
    private ObjectMapper mapper;  //For serialization/deserialization of json request/response
	
	@Autowired
    private PaymentFeignClient paymentFeignClient;

	@KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeOrder(String payment) throws JsonMappingException, JsonProcessingException {
		System.out.println("Received payment: " + payment);
//		String[] arr = payment.split(",");
		Payment paymentResponse = mapper.readValue(payment,Payment.class);
		try {
//				OrderDO fetchedOrderDo = repo.findByOrderId(arr[0]);
			    String orderId = paymentResponse.getOrderId();
			    String status = paymentResponse.getStatus();
			    OrderDO fetchedOrderDo = repo.findByOrderId(orderId);
				System.out.println(fetchedOrderDo);
//				fetchedOrderDo.setStatus(arr[1]);
				fetchedOrderDo.setStatus(paymentResponse.getStatus());
				repo.save(fetchedOrderDo);
				if(status.equals("Failed")) {
					System.out.println("Payment Failed for order id:"+orderId);
				}
				if(status.equals("Success")) {
					System.out.println("Payment Successful for order id:"+orderId);
				}
				if(status.equals("Pending")) {
					System.out.println("Payment Pending for order id:"+orderId);
				}
		} catch (Exception e) {
			System.out.println("Exception occured:" + e.getMessage());
		}
	}

	@Transactional
	public String orderProcessing(Order order) {
		OrderDO orderDo = new OrderDO();
		orderDo.setOrderId(order.getOrderId());
		orderDo.setOrderName(order.getOrderName());
		orderDo.setItemName(order.getItemName());
		orderDo.setItemType(order.getItemType());
		orderDo.setStatus("success");
		repo.save(orderDo);
		System.out.println("Order saved successfully.");
//		String url = "http://localhost:8083/payments/process?payment=" + order.toString();
//		restTemplate.getForObject(url, String.class);
		//Feign Client Changes starts
		Payment paymentRequest = new Payment();
		paymentRequest.setOrderId(order.getOrderId());
		paymentRequest.setPaymentAmount(order.getPaymentAmount());
		paymentRequest.setStatus("Pending");
        String response =
                paymentFeignClient.processPayment(paymentRequest);
        //Feign Client Changes ends
        if(response.equals("Error occurred in Payment")) {
           	return "Error in placing order";
        }
		System.out.println("Payment service called successfully");
		return "Order processed successfully";
	}
}

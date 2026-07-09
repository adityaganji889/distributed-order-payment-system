package com.app.orderservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.orderservice.dto.Payment;

@FeignClient(
        name = "payment-service",
        url = "http://localhost:8083"
)
public interface PaymentFeignClient {

    @PostMapping("/payments/process")
    public String processPayment(@RequestBody Payment request);

}
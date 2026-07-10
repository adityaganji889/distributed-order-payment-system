package com.app.orderservice.exceptionhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;

import feign.FeignException;
import feign.RetryableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex) {
//    	System.out.println("JsonProcessingException occurred:"+ex.getMessage());
    	logger.error("JsonProcessingException occurred:"+ex.getMessage());
        return "Invalid JSON received.";
    }

    @ExceptionHandler(KafkaConsumerCustomException.class)
    public String handleKafkaConsumerCustomException(KafkaConsumerCustomException ex) {
//    	System.out.println("KafkaConsumerCustomException occurred:"+ex.getMessage());
    	logger.error("KafkaConsumerCustomException occurred:"+ex.getMessage());
    	return "Kafka Consumer Error.";
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignException(FeignException ex) {
//    	System.out.println("FeignException occurred:"+ex.getMessage());
    	logger.error("FeignException occurred:"+ex.getMessage());
        return "Payment Service Error.";
    }

    @ExceptionHandler(RetryableException.class)
    public String handleRetryableException(RetryableException ex) {
//    	System.out.println("RetryableException occurred:"+ex.getMessage());
    	logger.error("RetryableException occurred:"+ex.getMessage());
        return "Payment Service Unavailable.";
    }

//    @ExceptionHandler(DataAccessException.class)
//    public String handleDataAccessException(DataAccessException ex) {
//    	System.out.println("DataAccessException occurred:"+ex.getMessage());
//        return "Database Error.";
//    }

    @ExceptionHandler(TransactionSystemException.class)
    public String handleTransactionSystemException(TransactionSystemException ex) {
//    	System.out.println("TransactionSystemException occurred:"+ex.getMessage());
    	logger.error("TransactionSystemException occurred:"+ex.getMessage());
        return "Transaction Failed.";
    }

//    @ExceptionHandler(NullPointerException.class)
//    public String handleNullPointerException(NullPointerException ex) {
//    	System.out.println("KafkaConsumerCustomException occurred:"+ex.getMessage());
//        return "Requested Order Not Found.";
//    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
//    	System.out.println("Exception occurred:"+ex.getMessage());
    	logger.error("Exception occurred:"+ex.getMessage());
        return "Internal Server Error.";
    }
}
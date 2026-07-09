package com.app.paymentservice.exceptionhandlers;

import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JsonProcessingException.class)
	public String handleJsonProcessingException(JsonProcessingException ex) {
		System.out.println("JsonProcessingException occurred:"+ex.getMessage());
	    return "Error occurred in Payment";
	}
	
	@ExceptionHandler(KafkaProducerCustomException.class)
	public String handleKafkaProducerCustomException(KafkaProducerCustomException ex) {
		System.out.println("KafkaProducerCustomException occurred:"+ex.getMessage());
		return "Error occurred in Payment";
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	public String handleTransactionSystemException(TransactionSystemException ex) {
		System.out.println("TransactionSystemException occurred:"+ex.getMessage());
	    return "Error occurred in Payment";
	}
	
	@ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
    	System.out.println("Exception occurred:"+ex.getMessage());
        return "Internal Server Error.";
    }
}

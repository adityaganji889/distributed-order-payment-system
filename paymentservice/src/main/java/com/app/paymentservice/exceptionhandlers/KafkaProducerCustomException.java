package com.app.paymentservice.exceptionhandlers;

public class KafkaProducerCustomException extends RuntimeException {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KafkaProducerCustomException(String message) {
		super(message);
	}
	
}


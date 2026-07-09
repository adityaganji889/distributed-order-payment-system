package com.app.orderservice.exceptionhandlers;

public class KafkaConsumerCustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KafkaConsumerCustomException(String message) {
		super(message);
	}
}

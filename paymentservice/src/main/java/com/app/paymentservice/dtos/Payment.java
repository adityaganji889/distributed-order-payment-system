package com.app.paymentservice.dtos;

public class Payment {

	private String orderId;
	private Double paymentAmount;
	private String status;
	
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(String orderId, Double paymentAmount, String status) {
		super();
		this.orderId = orderId;
		this.paymentAmount = paymentAmount;
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PaymentResponse [orderId=" + orderId + ", paymentAmount=" + paymentAmount + ", status=" + status + "]";
	}
	
}

package com.app.paymentservice.entities;

import jakarta.persistence.*;

@Entity
public class PaymentDO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String orderId;
	
	private Double paymentAmount;
	
	private String status;

	public PaymentDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentDO(Long id, String orderId, Double paymentAmount, String status) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.paymentAmount = paymentAmount;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "PaymentDO [id=" + id + ", orderId=" + orderId + ", paymentAmount=" + paymentAmount + ", status="
				+ status + "]";
	}
	
}

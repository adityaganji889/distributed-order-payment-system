package com.app.orderservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderDO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderId;

	private String orderName;

	private String itemName;

	private String itemType;

	private Double paymentAmount;

	private String status;

	public OrderDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDO(Long id, String orderId, String orderName, String itemName, String itemType, Double paymentAmount,
			String status) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.orderName = orderName;
		this.itemName = itemName;
		this.itemType = itemType;
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

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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
		return "OrderDO [id=" + id + ", orderId=" + orderId + ", orderName=" + orderName + ", itemName=" + itemName
				+ ", itemType=" + itemType + ", paymentAmount=" + paymentAmount + ", status=" + status + "]";
	}

	
}

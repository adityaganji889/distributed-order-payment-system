package com.app.orderservice.dto;

public class Order {

	private String orderId;
	private String orderName;
	private String itemName;
	private String itemType;
	private Double paymentAmount;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderId, String orderName, String itemName, String itemType, Double paymentAmount) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.itemName = itemName;
		this.itemType = itemType;
		this.paymentAmount = paymentAmount;
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

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + ", itemName=" + itemName + ", itemType="
				+ itemType + ", paymentAmount=" + paymentAmount + "]";
	}

	
}

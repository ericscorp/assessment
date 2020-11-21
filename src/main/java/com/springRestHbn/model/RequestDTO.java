package com.springRestHbn.model;

import java.math.BigDecimal;

public class RequestDTO {
	
	BigDecimal amount;
	String subscriptionType;
	String paymentDay;
	String startSubscriptionDate;
	String endSubscriptionDate;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	public String getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(String paymentDay) {
		this.paymentDay = paymentDay;
	}
	public String getStartSubscriptionDate() {
		return startSubscriptionDate;
	}
	public void setStartSubscriptionDate(String startSubscriptionDate) {
		this.startSubscriptionDate = startSubscriptionDate;
	}
	public String getEndSubscriptionDate() {
		return endSubscriptionDate;
	}
	public void setEndSubscriptionDate(String endSubscriptionDate) {
		this.endSubscriptionDate = endSubscriptionDate;
	}

	
}

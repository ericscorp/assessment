package com.springRestHbn.model;

import java.math.BigDecimal;
import java.util.List;

public class ResponseDTO {
	
	BigDecimal amount;
	String subscriptionType;
	List<String> invoiceDates;
	String status;
	String message;
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
	public List<String> getInvoiceDates() {
		return invoiceDates;
	}
	public void setInvoiceDates(List<String> invoiceDates) {
		this.invoiceDates = invoiceDates;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

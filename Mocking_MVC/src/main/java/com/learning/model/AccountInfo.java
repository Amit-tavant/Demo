package com.learning.model;

public class AccountInfo {

	private String accountId;
	private String customerName;
	private String customerNumber;
	private String debitCardNumber;
	public AccountInfo(String customerName, String customerNumber,
			String debitCardNumber) {
		
		this.customerName = customerName;
		this.customerNumber = customerNumber;
		this.debitCardNumber = debitCardNumber;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getDebitCardNumber() {
		return debitCardNumber;
	}
	public void setDebitCardNumber(String debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}
	
}

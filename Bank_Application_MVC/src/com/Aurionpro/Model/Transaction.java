package com.Aurionpro.Model;

import java.time.LocalDate;

public class Transaction {
	private String SenderAccNo;
	private String ReceiverAccNo;
	private String TypeOfTransaction;
	private double Amount;
	private LocalDate Date;
	
	public Transaction() {
		
	}
	
	public Transaction(String senderAccNo, String receiverAccNo, String typeOfTransaction, double amount,
			LocalDate date) {
		super();
		SenderAccNo = senderAccNo;
		ReceiverAccNo = receiverAccNo;
		TypeOfTransaction = typeOfTransaction;
		Amount = amount;
		Date = date;
	}
	
	public String getSenderAccNo() {
		return SenderAccNo;
	}
	public void setSenderAccNo(String senderAccNo) {
		SenderAccNo = senderAccNo;
	}
	public String getReceiverAccNo() {
		return ReceiverAccNo;
	}
	public void setReceiverAccNo(String receiverAccNo) {
		ReceiverAccNo = receiverAccNo;
	}
	public String getTypeOfTransaction() {
		return TypeOfTransaction;
	}
	public void setTypeOfTransaction(String typeOfTransaction) {
		TypeOfTransaction = typeOfTransaction;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public LocalDate getDate() {
		return Date;
	}
	public void setDate(LocalDate date) {
		Date = date;
	}
}

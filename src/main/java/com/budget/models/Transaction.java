package com.budget.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String transactionId;
	private Date date;
	private String description;
	private double amount;
	private String currency;

	@Transient
	private String accountId;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private Account account;

	public Transaction() {

	}

	public Transaction(String transactionId, Date date, String description, double amount, String currency,
			Account account, String accountId) {
		super();
		this.transactionId = transactionId;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.account = account;
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransaction(String transactionId) {
		this.transactionId = transactionId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + transactionId + ", date=" + date + ", description=" + description + ", amount="
				+ amount + ", currency=" + currency + ", accountId=" + account + "]";
	}

}
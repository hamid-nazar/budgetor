package com.budget.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "savings-goals")
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountId;
    private double goalAmount;
    private double currentAmount;
    private String description;
    private Date targetDate;
    
    

	public SavingsGoal() {

	}
	
	public SavingsGoal(String id, String accountId, double goalAmount, double currentAmount, String description,Date targetDate) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.goalAmount = goalAmount;
		this.currentAmount = currentAmount;
		this.description = description;
		this.targetDate = targetDate;
	}
    



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public double getGoalAmount() {
		return goalAmount;
	}


	public void setGoalAmount(double goalAmount) {
		this.goalAmount = goalAmount;
	}


	public double getCurrentAmount() {
		return currentAmount;
	}


	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	public Date getTargetDate() {
		return targetDate;
	}
	
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
}
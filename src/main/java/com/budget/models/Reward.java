package com.budget.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String savingsGoalId;
    private String name;
    private String description;
    
    
	public Reward(String id,String savingsGoalId, String name, String description) {
		super();
		this.id = id;
		this.savingsGoalId = savingsGoalId;
		this.name = name;
		this.description = description;
		
	}


	public Reward() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}





	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSavingsGoalId() {
		return savingsGoalId;
	}


	public void setSavingsGoalId(String savingsGoalId) {
		this.savingsGoalId = savingsGoalId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Reward [id=" + id + ", savingsGoalId=" + savingsGoalId + ", name=" + name + ", description="
				+ description + "]";
	}

}
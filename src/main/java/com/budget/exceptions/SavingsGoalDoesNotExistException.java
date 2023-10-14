package com.budget.exceptions;

public class SavingsGoalDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SavingsGoalDoesNotExistException() {
		super("The savings goal you are looking for does not exist");
	}

}

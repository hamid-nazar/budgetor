package com.budget.exceptions;

public class AccountDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccountDoesNotExistException() {
		
		super("The account you are looking for does not exist");
		
	}

}

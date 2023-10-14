package com.budget.exceptions;

public class TransactionDoesNotExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public TransactionDoesNotExistException() {
		super("The transaction you are looking for does not exist");
	}

}

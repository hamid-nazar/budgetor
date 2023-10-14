package com.budget.exceptions;

public class AccountIsAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccountIsAlreadyRegisteredException() {
		super("This account is already is registered!");
	}

}

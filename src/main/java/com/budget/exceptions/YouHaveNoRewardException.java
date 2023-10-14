package com.budget.exceptions;

public class YouHaveNoRewardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public YouHaveNoRewardException() {

		super("You have not won any rewards yet.");
	}

}

package com.budget.exceptions;

public class NoRewardAchievedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoRewardAchievedException() {
		super("You have not sarted yet on your savings goal");
	}
}

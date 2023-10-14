package com.budget.services;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.budget.models.SavingsGoal;

@Service
public class MilestoneService {

	private final EmailService emailService;
	private final AccountService accountService;
	private final SavingsGoalService savingsGoalService;

	public MilestoneService(EmailService emailService,SavingsGoalService savingsGoalService, AccountService accountService) {
		this.emailService = emailService;
		this.accountService = accountService;
		this.savingsGoalService = savingsGoalService;

	}

	// Execute the method every day at midnight (12:00 AM)
	// This method will send update to the user about their savings goals 
    @Scheduled(cron = "0 0 0 * * ?")
	public void scheduledTasks() {
    	
		// Replace "your-email@example.com" your own email address 
		String from = "your-email@example.com";
		String to = "";
		String subject = "";
		String message = "";

		List<SavingsGoal> savingsGoals = savingsGoalService.getAllSavingsGoals();

		for (SavingsGoal savingsGoal : savingsGoals) {

			if (savingsGoal.getGoalAmount() * 0.25 <= savingsGoal.getCurrentAmount()
					&& savingsGoal.getCurrentAmount() < savingsGoal.getGoalAmount() * 0.75) {

				to = accountService.getAccountById(savingsGoal.getAccountId()).getOwnerEmail();
				subject = "Bronze Reward";
				message = "Congratulations! You're about halfway through achieving your savings goal of : "
						+ savingsGoal.getGoalAmount() + " for " + savingsGoal.getDescription().toUpperCase();

				emailService.sendEmailNotification(to, from, subject, message);

				continue;
			} else if (savingsGoal.getGoalAmount() * 0.75 <= savingsGoal.getCurrentAmount()
					&& savingsGoal.getCurrentAmount() < savingsGoal.getGoalAmount()) {

				to = accountService.getAccountById(savingsGoal.getAccountId()).getOwnerEmail();
				subject = "Silver Reward";
				message = "Congratulations! You're just a step away from reaching your savings goal of: "
						+ savingsGoal.getGoalAmount() + " for " + savingsGoal.getDescription().toUpperCase();

				emailService.sendEmailNotification(to, from, subject, message);

				continue;

			} else if (savingsGoal.getCurrentAmount() >= savingsGoal.getGoalAmount()) {

				to = accountService.getAccountById(savingsGoal.getAccountId()).getOwnerEmail();
				subject = "Gold Reward";
				message = "Congratulations, you've successfully reached your savings goal of: "
						+ savingsGoal.getGoalAmount() + " for " + savingsGoal.getDescription().toUpperCase();

				emailService.sendEmailNotification(to, from, subject, message);
			}

		}
	}

}

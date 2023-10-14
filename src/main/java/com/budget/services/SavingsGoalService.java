package com.budget.services;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.budget.exceptions.NoRewardAchievedException;
import com.budget.exceptions.SavingsGoalDoesNotExistException;
import com.budget.exceptions.TransactionDoesNotExistException;
import com.budget.exceptions.YouHaveNoRewardException;
import com.budget.models.Account;
import com.budget.models.Reward;
import com.budget.models.SavingsGoal;
import com.budget.models.Transaction;
import com.budget.repositories.RewardRepo;
import com.budget.repositories.SavingsGoalRepo;

@Service
public class SavingsGoalService {

	private final SavingsGoalRepo savingsGoalRepo;
	private final TransactionService transactionService;
	private final AccountService accountService;
	private final RewardRepo rewardRepo;

	@Autowired
	public SavingsGoalService(SavingsGoalRepo savingsGoalRepo, RewardRepo rewardRepo,
			TransactionService transactionService, AccountService accountService) {
		this.savingsGoalRepo = savingsGoalRepo;
		this.transactionService = transactionService;
		this.accountService = accountService;
		this.rewardRepo = rewardRepo;

	}

	public SavingsGoal createSavingsGoal(SavingsGoal savingsGoal) {

		return savingsGoalRepo.save(savingsGoal);

	}

	public SavingsGoal addDepositToSavingsGoal(String savingsGoalId, double amount) {

		SavingsGoal savingsGoal = savingsGoalRepo.findById(savingsGoalId)
				.orElseThrow(SavingsGoalDoesNotExistException::new);
		savingsGoal.setCurrentAmount(savingsGoal.getCurrentAmount() + amount);

		return savingsGoalRepo.save(savingsGoal);
	}

	public List<SavingsGoal> getSavingsGoalsByAccountId(String accountId) {

		return savingsGoalRepo.findByAccountId(accountId).orElseThrow(TransactionDoesNotExistException::new);

	}

	public SavingsGoal findSavingsGoalById(String id) {

		return savingsGoalRepo.findById(id).orElseThrow(SavingsGoalDoesNotExistException::new);
	}

	public void deleteSavingsGoalById(String savingsGoalId) {

		savingsGoalRepo.deleteById(savingsGoalId);

	}

	public String checkStatus(String id) {

		SavingsGoal savingsGoal = savingsGoalRepo.findById(id).orElseThrow(SavingsGoalDoesNotExistException::new);

		String result = "You have achieved "
				+ Math.round((savingsGoal.getCurrentAmount() / savingsGoal.getGoalAmount()) * 100) + "%"
				+ " of your goal, which is " + savingsGoal.getGoalAmount() + " for "
				+ savingsGoal.getDescription().toUpperCase();

		return result;
	}

	public String suggestSavingsGoal(String savingsGoalId) {

		String result = "";

		SavingsGoal savingsGoal = savingsGoalRepo.findById(savingsGoalId)
				.orElseThrow(SavingsGoalDoesNotExistException::new);
		
		List<Transaction> transactions = transactionService.getTransactionsByAccount(accountService.getAccountById(savingsGoal.getAccountId()));
		LocalDate currentDate = LocalDate.now();

		List<Transaction> enoughData = transactions.stream()
				.filter(a -> ChronoUnit.MONTHS.between(a.getDate().toLocalDate(), currentDate) >= 12)
				.collect(Collectors.toList());

		if (!(enoughData.size() >= 12)) {

			result = "We dont have enough data to give you a valid suggestion on how much you need "
					+ "to save each month to reach your savings goal";

			return result;
		}

		LocalDate targetDate = savingsGoal.getTargetDate().toLocalDate();

		int months = (int) ChronoUnit.MONTHS.between(currentDate, targetDate);;
		
		 DecimalFormat df = new DecimalFormat("#.##");

//	    	Assuming that the use has at least one year of bank transaction data saved in the database
//			Calculates the average monthly income for the past year, and then makes assumption on how  much
//			the user needs to save each month in order for them to reach their goal

		double averageMonthlyIncome = transactions.stream()
				.filter(a -> ChronoUnit.MONTHS.between(currentDate, targetDate) <= 12)
				.map(a -> a.getAmount())
				.filter(value -> value > 0)
				.reduce((a, b) -> a + b).get()/12;

		double monthlySaving = savingsGoal.getGoalAmount() / months;

		double monthlyPercentageSaving = (monthlySaving / averageMonthlyIncome) * 100;

		result = "You need to save at least " + df.format(monthlyPercentageSaving)
				+ "% of your monthly income in order for you to reach your goal of saving "
				+ savingsGoal.getGoalAmount()
				+ " before the target date. This suggestion is based on your entire income transaction data for the past year.";

		return result;
	}
	
	public List<SavingsGoal> getAllSavingsGoals() {
		
		return (List<SavingsGoal>) savingsGoalRepo.findAll();
	}

	public Reward rewardForSavingsGoal(String savingsGoalId) {

		SavingsGoal savingsGoal = savingsGoalRepo.findById(savingsGoalId)
				.orElseThrow(SavingsGoalDoesNotExistException::new);

		Reward reward = new Reward();

		if ( savingsGoal.getGoalAmount()*0.25 <= savingsGoal.getCurrentAmount() 
			&& savingsGoal.getCurrentAmount() < savingsGoal.getGoalAmount() * 0.75) {
			
			reward.setSavingsGoalId(savingsGoalId);
			reward.setName("Bronze Reward");
			reward.setDescription(
					"You have achieved a bronze savings milestone for your goal of " + savingsGoal.getDescription());

			return rewardRepo.save(reward);
			
		} else if (savingsGoal.getGoalAmount()*0.75 <= savingsGoal.getCurrentAmount() 
				 && savingsGoal.getCurrentAmount() < savingsGoal.getGoalAmount()) {
			
			reward.setSavingsGoalId(savingsGoalId);
			reward.setName("Silver Reward");
			reward.setDescription(
					"You have achieved a silver savings milestone for your goal of " + savingsGoal.getDescription());

			return rewardRepo.save(reward);
			
		} else if (savingsGoal.getCurrentAmount() >= savingsGoal.getGoalAmount()) {
			
			reward.setSavingsGoalId(savingsGoalId);
			reward.setName("Gold Reward");
			reward.setDescription("You have achieved a gold savings milestone,"
					+ " which means you have successfull reached your savings goal for " + savingsGoal.getDescription());

			return rewardRepo.save(reward);
			
		} else {
			throw new NoRewardAchievedException();
		}

	}

	public List<Reward> getAllYouRewards() {

		List<Reward> rewards = (List<Reward>) rewardRepo.findAll();

		if (rewards.size() == 0) {

			throw new YouHaveNoRewardException();
		}

		return rewards;
	}
}

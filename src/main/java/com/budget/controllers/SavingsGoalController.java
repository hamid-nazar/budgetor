package com.budget.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.exceptions.NoRewardAchievedException;
import com.budget.exceptions.SavingsGoalDoesNotExistException;
import com.budget.models.Reward;
import com.budget.models.SavingsGoal;
import com.budget.models.Transaction;
import com.budget.repositories.SavingsGoalRepo;
import com.budget.services.SavingsGoalService;
import com.budget.services.TransactionService;

@RestController
@RequestMapping("/savings-goal")
public class SavingsGoalController {

	private final SavingsGoalService savingsGoalService;

	public SavingsGoalController(SavingsGoalService savingsGoalService) {

		this.savingsGoalService = savingsGoalService;

	}

	@PostMapping("/create")
	public SavingsGoal createSavingsGoal(@RequestBody SavingsGoal savingsGoal) {
		return savingsGoalService.createSavingsGoal(savingsGoal);
	}

	@ExceptionHandler(SavingsGoalDoesNotExistException.class)
	public ResponseEntity<String> handleSavingsGoalDoesntExist() {

		return new ResponseEntity<String>("The savings goal you are looking for does not exist", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/deposit")
	public SavingsGoal deposit(@RequestBody LinkedHashMap<String, String> body) {
		
		String savingsGoalId = body.get("savingsGoalId");
		double amount = Double.valueOf(body.get("amount"));

		return savingsGoalService.addDepositToSavingsGoal(savingsGoalId, amount);
	}

	@GetMapping("/status/{savingsGoalId}")
	public ResponseEntity<String> savingsGoalStatus(@PathVariable("savingsGoalId") String savingsGoalId) {

		String status = savingsGoalService.checkStatus(savingsGoalId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@GetMapping("/all/{accoundId}")
	public List<SavingsGoal> getSavingGoals(@PathVariable("savingsGoalId") String accoundId) {

		return savingsGoalService.getSavingsGoalsByAccountId(accoundId);
	}

	@GetMapping("/specific/{savingsGoalId}")
	public SavingsGoal getSavingGoal(@PathVariable("savingsGoalId") String savingsGoalId) {

		return savingsGoalService.findSavingsGoalById(savingsGoalId);
	}

	@PutMapping("/update")
	public SavingsGoal updateSavingGoal(@RequestBody SavingsGoal savingGoal) {

		return savingsGoalService.createSavingsGoal(savingGoal);
	}

	@DeleteMapping("/delete/{savingsGoalId}")
	public ResponseEntity<String> deleteSavingGoal(@PathVariable("savingsGoalId") String savingsGoalId) {

		savingsGoalService.deleteSavingsGoalById(savingsGoalId);

		return new ResponseEntity<String>("Succefully deleleted the savings goal", HttpStatus.OK);
	}

	@GetMapping("/suggestion/{savingsGoalId}")
	public ResponseEntity<String> suggestSavingsGoal(@PathVariable("savingsGoalId") String savingsGoalId) {
		
		return ResponseEntity.ok(savingsGoalService.suggestSavingsGoal(savingsGoalId));
	}

	@GetMapping("/reward/{savingsGoalId}")
	public Reward getReward(@PathVariable("savingsGoalId") String savingsGoalId) {

		return savingsGoalService.rewardForSavingsGoal(savingsGoalId);
	}

	@ExceptionHandler(NoRewardAchievedException.class)
	public ResponseEntity<String> handleGotNotRewards() {

		return ResponseEntity.ok("You have not won any rewards yet.");
	}

	@GetMapping("/rewards")
	public List<Reward> getAllRewards() {

		return savingsGoalService.getAllYouRewards();
	}
}

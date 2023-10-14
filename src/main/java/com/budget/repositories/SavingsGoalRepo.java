package com.budget.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.models.SavingsGoal;

@Repository
public interface SavingsGoalRepo extends CrudRepository<SavingsGoal, String> {

	Optional<List<SavingsGoal>> findByAccountId(String accountId);
	Optional<SavingsGoal> findByDescription(String description);
	
}
package com.budget.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.models.Account;
import com.budget.models.Transaction;

@Repository
public interface AccountRepo extends CrudRepository<Account, String> {
	
	Optional<Account> findByAccountNumber(String accountNumber);
}
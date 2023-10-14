package com.budget.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budget.models.Account;
import com.budget.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {
	
    Optional<List<Transaction>> findByAccount(Account account);
}


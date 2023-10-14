package com.budget.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.exceptions.TransactionDoesNotExistException;
import com.budget.models.Account;
import com.budget.models.Transaction;
import com.budget.repositories.TransactionRepo;

@Service
public class TransactionService {

	private final TransactionRepo transactionRepo;
	private final AccountService accountService;

	@Autowired
	public TransactionService(TransactionRepo transactionRepo, AccountService accountService) {
		this.transactionRepo = transactionRepo;
		this.accountService = accountService;

	}

	public Transaction createTransaction(Transaction transaction, String accountId) {
		Transaction newTransaction = transaction;
		newTransaction.setAccount(accountService.getAccountById(accountId));
		return transactionRepo.save(newTransaction);

	}

	public List<Transaction> getTransactionsByAccount(Account account) {

		List<Transaction> transactions = transactionRepo.findByAccount(account).get();

		if (transactions.size() == 0) {
			new TransactionDoesNotExistException();
		}
		for (Transaction transaction : transactions) {
			transaction.setAccountId(account.getAccountId());;
		}

		return transactions;

	}

	public Transaction geTransactionById(String id) {

		return transactionRepo.findById(id).orElseThrow(TransactionDoesNotExistException::new);
	}

	public void deleteTransactionById(String id) {

		transactionRepo.deleteById(id);
	}

}

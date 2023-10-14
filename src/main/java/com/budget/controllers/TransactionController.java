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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.exceptions.TransactionDoesNotExistException;
import com.budget.models.Account;
import com.budget.models.Transaction;
import com.budget.services.AccountService;
import com.budget.services.TransactionService;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private final TransactionService transactionService;
	private final AccountService accountService;

	public TransactionController(TransactionService transactionService, AccountService accountService) {

		this.transactionService = transactionService;
		this.accountService = accountService;

	}

	@ExceptionHandler(TransactionDoesNotExistException.class)
	public ResponseEntity<String> handelTransactionDoesntExist() {

		return new ResponseEntity<String>("The transaction you are looking for does not exist", HttpStatus.NOT_FOUND);

	}

	@PostMapping("/create/{accountId}")
	public Transaction createTransaction(@PathVariable("accountId") String accountId, @RequestBody Transaction transaction) {
		
		return transactionService.createTransaction(transaction, accountId);
	}

	@GetMapping("/all/{accountId}")
	public List<Transaction> getTransactions(@PathVariable("accountId") String accountId) {

		Account account = accountService.getAccountById(accountId);

		return transactionService.getTransactionsByAccount(account);
	}

	@PostMapping("/specific")
	public Transaction getTransaction(@RequestBody LinkedHashMap<String, String> body) {

		String transactionId = body.get("transactionId");

		return transactionService.geTransactionById(transactionId);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteTransaction(@RequestBody LinkedHashMap<String, String> body) {

		String transactionId = body.get("transactionId");
		transactionService.deleteTransactionById(transactionId);

		return new ResponseEntity<String>("Successfully deleted the transaction", HttpStatus.OK);
	}

}

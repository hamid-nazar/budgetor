package com.budget.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.exceptions.AccountDoesNotExistException;
import com.budget.exceptions.AccountIsAlreadyRegisteredException;
import com.budget.models.Account;
import com.budget.repositories.AccountRepo;

@Service
public class AccountService {
	private final AccountRepo accountRepo;

	@Autowired
	public AccountService(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;

	}

	public Account RegisterAccount(Account account) {

		String accountNumber = account.getAccountNumber();
		Account acc = accountRepo.findByAccountNumber(accountNumber).orElse(null);

		System.out.println(accountNumber);

		if (acc != null) {
			throw new AccountIsAlreadyRegisteredException();

		}

		return accountRepo.save(account);

	}

	public Account getAccountById(String id) {

		return accountRepo.findById(id).orElseThrow(AccountDoesNotExistException::new);

	}

	public Account updatAccount(Account account) {

		return RegisterAccount(account);
	}

	public void deleteAccount(String id) {

		Account account = getAccountById(id);
		accountRepo.delete(account);

	}

	public List<Account> getAllAccounts() {
		return (List<Account>) accountRepo.findAll();
	}
}

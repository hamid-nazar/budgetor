package com.budget.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.exceptions.AccountDoesNotExistException;
import com.budget.exceptions.AccountIsAlreadyRegisteredException;
import com.budget.models.Account;
import com.budget.services.AccountService;


@RestController
@RequestMapping("/account")
public class AccountController {
	 private final AccountService accountService;
	 
	 @Autowired
	 public AccountController(AccountService accountService) {
		 
		this.accountService = accountService;
	}
	 
	 
	 @ExceptionHandler(AccountIsAlreadyRegisteredException.class)
	    public ResponseEntity<String> handleAccountAlreadyRegistered() {
			
	    	return new ResponseEntity<String>("This account is already registered! Try A different account.",HttpStatus.CONFLICT);
	    	
		}
	    @PostMapping("/register")
	    public Account RegisterAccount(@RequestBody Account account) {
	    	
	        return accountService.RegisterAccount(account);
	    }

	    @ExceptionHandler(AccountDoesNotExistException.class)
	   public ResponseEntity<String> handelAccountDoesntExist(){
	    	
		return new ResponseEntity<String>("The account you are looking for does not exist", HttpStatus.NOT_FOUND);
	    	
	    }
	    
	    @GetMapping("/all")
	    public List<Account> getAccounts() {
	    
	        return accountService.getAllAccounts();
	    }
	    
	    @PostMapping("/specific")
	    public Account getAccount(@RequestBody LinkedHashMap<String, String> body) {
	    	
	    	String accountId = body.get("accountId");
	    	
	        return accountService.getAccountById(accountId);
	    }

	    @PutMapping("/update")
	    public Account updateAccount(@RequestBody Account account) {
	      
	        return accountService.updatAccount(account);
	    }

	    @DeleteMapping("/delete")
	    public ResponseEntity<String> deleteAccount(@RequestBody LinkedHashMap<String, String> body) {
	    	
	    	String accountId = body.get("accountId");
	    	
	        accountService.deleteAccount(accountId);
	        
	        return new ResponseEntity<String>("Successfully deleted the account",HttpStatus.OK);
	    }


}

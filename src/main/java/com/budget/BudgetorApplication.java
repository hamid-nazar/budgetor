package com.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BudgetorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetorApplication.class, args);
	}

}

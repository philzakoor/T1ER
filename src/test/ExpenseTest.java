package test;

import java.math.BigDecimal;

import beans.Expense;
import beans.Tier;
import beans.User;
import builders.ExpenseBuilder;
import builders.TierBuilder;

public class ExpenseTest {
	public static void main(String[] args) {
		User user = new User("password", "phil@email.com");
		Tier tier = new TierBuilder()
				.setName("Tier 1")
				.setOwner(user)
				.createTier();
		BigDecimal amount = new BigDecimal(982.34);

		Expense expense = new ExpenseBuilder()
				.setName("Shoppers trip")
				.setDescription("Shoppers trip")
				.setAmount(amount)
				.setOwner(user)
				.createExpense();
		System.out.println(expense);
		
		tier.addExpense(expense);
		tier.getExpenses().forEach((n) -> System.out.print(n.getName() + " amount: " + n.getAmount().toString()));
	}
}

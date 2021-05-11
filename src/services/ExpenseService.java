package services;

import java.util.Map;
import java.util.UUID;

import beans.Expense;

public interface ExpenseService {
	
	public Map<UUID, Expense> readExpenses(String tierId);
	public Expense readExpense(String id);
	public void createExpense(Expense expense, String tierId);
	public void updateExpense(Expense expense, String tierId);
	public void deleteExpense(String id);
	public void createOrUpdateExpense(Expense expense, String tierId);
	public Map<UUID, Expense> getRecentExpenses(String tierId);
}

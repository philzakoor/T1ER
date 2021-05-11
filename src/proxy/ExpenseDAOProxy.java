package proxy;

import java.util.Map;
import java.util.UUID;

import beans.Expense;
import beans.Notification;
import beans.Tier;
import dao.ExpenseServiceDAO;
import dao.NotificationServiceDAO;
import dao.TierServiceDAO;
import services.ExpenseService;
import services.NotificationService;
import services.TierService;

public class ExpenseDAOProxy implements ExpenseService {

	ExpenseService expenses;
	NotificationService notifications;
	TierService tiers;
	
	public ExpenseDAOProxy() {
		expenses = new ExpenseServiceDAO();
		notifications = new NotificationServiceDAO();
		tiers = new TierServiceDAO();
	}
	
	@Override
	public Map<UUID, Expense> readExpenses(String tierId) {
		return expenses.readExpenses(tierId);
	}

	@Override
	public Expense readExpense(String id) {
		return expenses.readExpense(id);
	}

	@Override
	public void createExpense(Expense expense, String tierId) {
		expenses.createExpense(expense, tierId);
		Tier tier = tiers.readTier(tierId);
		tier.registerObservers(notifications.getObservers(expense.getOwner().getId().toString(), tierId));
		String message = "New expense created for Tier: " + tier.getName();
		Notification notification = new Notification(message);
		tier.notifyObservers(notification);
	}

	@Override
	public void updateExpense(Expense expense, String tierId) {
		expenses.updateExpense(expense, tierId);
		Tier tier = tiers.readTier(tierId);
		tier.registerObservers(notifications.getObservers(expense.getOwner().getId().toString(), tierId));
		String message = "Tier: " + tier.getName() + " ---- Updated expense: " + expense.getName();
		Notification notification = new Notification(message);
		tier.notifyObservers(notification);
	}

	@Override
	public void deleteExpense(String id) {
		expenses.deleteExpense(id);
	}

	@Override
	public void createOrUpdateExpense(Expense expense, String tierId) {
		Expense localExpense = readExpense(expense.getId().toString());
		if (localExpense == null) {
			createExpense(expense, tierId);
		} else {
			updateExpense(expense, tierId);
		}
	}
	
	@Override
	public Map<UUID, Expense> getRecentExpenses(String tierId){
		return expenses.getRecentExpenses(tierId);
	}

}

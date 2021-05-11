package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import beans.Expense;
import beans.Tier;
import beans.User;

public interface TierService {
	
	public Map<UUID, Tier> readTiers(String userId);
	public Tier readTier(String id);
	public void createTier(Tier tier);
	public void updateTier(Tier tier);
	public void deleteTier(String id);
	public void createOrUpdateTier(Tier tier);
	public ArrayList<Expense> readExpenses(String tierId);
	public User readOwner(String tierId);
	public String getTierExpenseTotal(String tierId);
}

package services;

import java.util.Map;
import java.util.UUID;

import beans.Expense;
import beans.Tier;

public interface OwnerService {

	Map<UUID, Expense> readOwnedExpense(String userId, String tierId);
	Map<UUID, Expense> readUnownedExpense(String userId, String tierId);
	Map<UUID, Tier> readOwnedOrUnownedTiers(String userId, Boolean isOwner);
}

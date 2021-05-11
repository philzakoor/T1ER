package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import beans.Expense;
import beans.Tier;
import proxy.ExpenseDAOProxy;
import services.ExpenseService;
import services.OwnerService;
import services.TierService;

public class OwnerServiceDAO implements OwnerService {
	
	ExpenseService expenses;
	
	public OwnerServiceDAO() {
		this.expenses = new ExpenseDAOProxy();
	}
	
	@Override
	public Map<UUID, Expense> readOwnedExpense(String userId, String tierId){
		Expense expense;
		Map<UUID, Expense> userExpenses = new LinkedHashMap<UUID, Expense>();
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "select * from expense where tier = ? and owner = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			stmt.setString(2, userId);
			
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				expense = expenses.readExpense(set.getString("id"));
				userExpenses.put(expense.getId(), expense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userExpenses;
	}
	
	@Override
	public Map<UUID, Expense> readUnownedExpense(String userId, String tierId){
		Expense expense;
		Map<UUID, Expense> userExpenses = new LinkedHashMap<UUID, Expense>();
		
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "select * from expense where tier = ? and owner <> ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			stmt.setString(2, userId);
			
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				expense = expenses.readExpense(set.getString("id"));
				userExpenses.put(expense.getId(), expense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userExpenses;
	}
	
	@Override
	public Map<UUID, Tier> readOwnedOrUnownedTiers(String userId, Boolean isOwner) {
		Map<UUID, Tier> tiers = new LinkedHashMap<UUID, Tier>();
		Tier tier;
		TierService tierService = new TierServiceDAO();

		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "select * from tier_members tm \r\n" + "left join tier t \r\n" + "on tm.tierId = t.id\r\n"
					+ "where tm.userId = ? and isOwner = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setBoolean(2, isOwner);
			ResultSet set = stmt.executeQuery();

			while (set.next()) {
				tier = tierService.readTier(set.getString("id"));
				tiers.put(tier.getId(), tier);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tiers;
	}
}

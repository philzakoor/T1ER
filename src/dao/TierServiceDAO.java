package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import beans.Expense;
import beans.Tier;
import beans.User;
import builders.TierBuilder;
import proxy.ExpenseDAOProxy;
import services.ExpenseService;
import services.MemberService;
import services.TierService;
import services.UserService;

public class TierServiceDAO implements TierService {

	@Override
	public Map<UUID, Tier> readTiers(String userId) {
		Tier tier = null;
		Map<UUID, Tier> tiers = new LinkedHashMap<UUID, Tier>();

		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "SELECT * FROM tier_members TM\r\n" + "LEFT JOIN tier T\r\n" + "ON TM.tierID = T.id\r\n"
					+ "WHERE userID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				tier = readTier(set.getString("tierId"));
				tiers.put(tier.getId(), tier);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tiers;
	}

	@Override
	public Tier readTier(String id) {
		Tier tier = null;
		MemberService members = new MemberServiceDAO();

		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write and execute SQL statement to get user
			String sql = "select * from tier T left join tier_members TM on T.id = TM.tierId where T.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet set = stmt.executeQuery();

			// create Tier object
			while (set.next()) {
				tier = new TierBuilder()
				.setId(UUID.fromString(set.getString("id")))
				.setName(set.getString("name"))
				.setOwner(readOwner(id))
				.setMembers(members.readMembers(set.getString("id")))
				.createTier();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tier;
	}

	@Override
	public void createTier(Tier tier) {
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// sql insert query for Tier
			String sql = "insert into tier (id, name) values (?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tier.getId().toString());
			stmt.setString(2, tier.getName());
			stmt.executeUpdate();

			// sql insert query for tier_memebers
			for (User user : tier.getMembers()) {
				boolean isOwner = false;
				if (tier.getOwner().getId() == user.getId())
					isOwner = true;
				sql = "insert into tier_members (tierId, userId, isOwner) values (?, ?, ?);";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, tier.getId().toString());
				stmt.setString(2, user.getId().toString());
				stmt.setBoolean(3, isOwner);
				stmt.executeUpdate();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTier(Tier tier) {
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// sql insert query for Tier
			String sql = "update tier set name = ? where id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tier.getName());
			stmt.setString(2, tier.getId().toString());
			stmt.executeUpdate();

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTier(String id) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "delete from tier_members where tierId = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();

			sql = "delete from expense where tier = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();

			sql = "delete from tier where id = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();

			conn.close();
			System.out.println("deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createOrUpdateTier(Tier tier) {
		Tier localTier = readTier(tier.getId().toString());
		if (localTier == null) {
			createTier(tier);
		} else {
			updateTier(tier);
		}
	}

	@Override
	public ArrayList<Expense> readExpenses(String tierId) {
		ArrayList<Expense> expenses = new ArrayList<Expense>();
		ExpenseService expenseService = new ExpenseDAOProxy();

		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write sql to get members + owner
			String sql = "select * from expense where tierId = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			ResultSet set = stmt.executeQuery();

			// create owner and members
			while (set.next()) {
				Expense expense = expenseService.readExpense(set.getString("id"));
				expenses.add(expense);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenses;
	}

	@Override
	public User readOwner(String tierId) {
		User user = null;
		UserService userService = new UserServiceDAO();

		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write sql to get members + owner
			String sql = "select * from tier_members where tierId = ? and isOwner = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			stmt.setBoolean(2, true);
			ResultSet set = stmt.executeQuery();

			// create owner and members
			while (set.next()) {
				user = userService.readUser(set.getString("userId"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public String getTierExpenseTotal(String tierId) {
		String expenseTotal = "";
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write sql to get members + owner
			String sql = "select sum(amount) as total from expense where tier = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			ResultSet set = stmt.executeQuery();

			// create owner and members
			while (set.next()) {
				expenseTotal = set.getString("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (expenseTotal == null)
			expenseTotal = "0.00";
		return expenseTotal;
	}
}

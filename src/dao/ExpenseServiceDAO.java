package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import beans.Expense;
import builders.ExpenseBuilder;
import services.ExpenseService;
import services.UserService;

public class ExpenseServiceDAO implements ExpenseService {
	
	@Override
	public Map<UUID, Expense> readExpenses(String tierId) {
		Expense expense = null;
		Map<UUID, Expense> expenses = new LinkedHashMap<UUID, Expense>();
	
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "select * from expense where tier = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				expense = readExpense(set.getString("id"));
				expenses.put(expense.getId(), expense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenses;
	}

	@Override
	public Expense readExpense(String id) {
		Expense expense = null;
		UserService userService = new UserServiceDAO();
		
		try {
			//get connection
			Connection conn = DBConnection.getConnectionToDatabase();
			
			//write SQL query to read Expense
			String sql = "select * from expense where id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				expense = new ExpenseBuilder()
				.setId(UUID.fromString(set.getString("id")))
				.setAmount(set.getBigDecimal("amount"))
				.setName(set.getString("name"))
				.setDescription(set.getString("description"))
				.setOwner(userService.readUser(set.getString("owner")))
				.setCreatedDate(set.getTimestamp("createdDate"))
				.createExpense();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expense;
	}

	@Override
	public void createExpense(Expense expense, String tierId) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "insert into expense (id, name, description, amount, owner, createdDate, tier) values (?,?,?,?,?,?,?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, expense.getId().toString());
			stmt.setString(2, expense.getName());
			stmt.setString(3, expense.getDescription());
			stmt.setBigDecimal(4, expense.getAmount());
			stmt.setString(5, expense.getOwner().getId().toString());
			stmt.setTimestamp(6, expense.getCreatedDate());
			stmt.setString(7, tierId);
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateExpense(Expense expense, String tierId) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "update expense set name = ?, description = ?, amount = ? where id= ? and tier = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, expense.getName());
			stmt.setString(2, expense.getDescription());
			stmt.setBigDecimal(3, expense.getAmount());
			stmt.setString(4, expense.getId().toString());
			stmt.setString(5, tierId);
			
			stmt.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

	@Override
	public void deleteExpense(String id) {
        try {
            // get connection to database
            Connection conn = DBConnection.getConnectionToDatabase();

            // write select query to get the log
            String sql = "delete from expense where id=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);

            stmt.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
	public Map<UUID, Expense> getRecentExpenses(String tierId) {
		Expense expense = null;
		Map<UUID, Expense> expenses = new LinkedHashMap<UUID, Expense>();
	
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "select * from expense where tier = ? order by createdDate limit 4";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				expense = readExpense(set.getString("id"));
				expenses.put(expense.getId(), expense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenses;
	}
}

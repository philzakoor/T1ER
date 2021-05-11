package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.User;
import services.MemberService;
import services.UserService;

public class MemberServiceDAO implements MemberService {

	@Override
	public void addUser(String email, String tierId) {
		UserService userService = new UserServiceDAO();
		User user = userService.searchUser(email);
		Boolean isOwner = false;
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "insert into tier_members (tierId, userId, isOwner) values (?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			stmt.setString(2, user.getId().toString());
			stmt.setBoolean(3, isOwner);
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUser(String email, String tierId) {
		UserService userService = new UserServiceDAO();
		User user = userService.searchUser(email);
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "delete from tier_members where userId = ? and tierId = ? and isOwner = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getId().toString());
			stmt.setString(2, tierId);
			stmt.setBoolean(3, false);
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addOrRemoveUser(String email, String tierId) {
		User member = searchMember(email, tierId);
		if (member.getId() != null) {
			removeUser(email, tierId);
		} else {
			addUser(email, tierId);
		}
	}

	@Override
	public ArrayList<User> readMembers(String tierId) {
		ArrayList<User> members = new ArrayList<User>();
		UserService userService = new UserServiceDAO();

		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write sql to get members + owner
			String sql = "select * from tier_members where tierId = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			ResultSet set = stmt.executeQuery();

			// create owner and members
			while (set.next()) {
				User member = userService.readUser(set.getString("userId"));
				members.add(member);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	@Override
	public User searchMember(String email, String tierId) {
		User user = new User();
		UserService userService = new UserServiceDAO();
		String userId = userService.searchUser(email).getId().toString();
		
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			String sql = "select * from tier_members where tierId = ? and userId = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, tierId);
			stmt.setString(2, userId);
			ResultSet set = stmt.executeQuery();

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

}

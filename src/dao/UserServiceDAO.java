package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import beans.EmailVerification;
import beans.KeyGenerator;
import beans.User;
import services.UserService;

public class UserServiceDAO implements UserService {

	@Override
	public void createOrUpdateUser(User user) {
		User localUser = readUser(user.getId().toString());
		if (localUser == null) {
			createUser(user);
		} else {
			updateUser(user);
		}
	}

	@Override
	public User readUser(String id) {
		User user = null;
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write SQL statement to get user
			String sql = "select * from user where id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			// execute, get resultSet, and return user info
			ResultSet set = stmt.executeQuery();
			if (set != null) {
				while (set.next()) {
					user = new User();
					user.setID(UUID.fromString(set.getString("id")));
					user.setEmail(set.getString("email"));
					user.setPassword(set.getString("password"));
					user.setIsActivated(set.getBoolean("isActivated"));
					user.setCreateDate(set.getTimestamp("createddate"));
					user.setDeleteDate(set.getTimestamp("deleteddate"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void updateUser(User user) {
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// sql update query for user
			String sql = "update user set email = ?,password = ?,deletedDate = ?  where id=?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setTimestamp(3, user.getDeletedDate());
			stmt.setString(4, user.getId().toString());

			// execute and close connection
			stmt.executeLargeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createUser(User user) {
		String key = KeyGenerator.getInstance().key();
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// sql insert query for user
			String sql = "insert into user (id, email, password, createdDate, deletedDate, isActivated, activationKey) values (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getId().toString());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setTimestamp(4, user.getCreatedDate());
			stmt.setTimestamp(5, user.getDeletedDate());
			stmt.setBoolean(6, false);
			stmt.setString(7, key);

			// execute and close connection
			stmt.executeUpdate();
			conn.close();

			EmailVerification.sendEmailVerifaction(user.getEmail(), key);
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void permenentDeleteUser(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User searchUser(String email) {
		User user = null;
		try {
			// get connection to database
			Connection conn = DBConnection.getConnectionToDatabase();

			// write SQL statement to get user
			String sql = "select * from user where email = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);

			// execute, get resultSet, and return user info
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				user = readUser(set.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void activateUser(String key) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "update user set isActivated = ?, activationKey = ? where activationKey = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, true);
			stmt.setNull(2, java.sql.Types.VARCHAR);
			stmt.setString(3, key);

			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resendActivation(User user) {
		String key = KeyGenerator.getInstance().key();
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "update user set activationKey = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, key);
			stmt.setString(2, user.getId().toString());
			
			stmt.executeUpdate();
			conn.close();
			
			EmailVerification.sendEmailVerifaction(user.getEmail(), key);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public User searchKey(String key) {
		User user = null;
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "Select * from user where activationKey = ? and isActivated = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, key);
			stmt.setBoolean(2, true);

			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				user = readUser(set.getString("id"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public void changeUserPassword(User user) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "update user set password = ?, activationKey = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getPassword());
			stmt.setNull(2, java.sql.Types.VARCHAR);
			stmt.setString(3, user.getId().toString());

			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setUserKey(User user, String key) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "update user set activationKey = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, key);
			stmt.setString(2, user.getId().toString());

			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

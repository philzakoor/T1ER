package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import beans.Notification;
import beans.User;
import observer.Observer;
import services.NotificationService;
import services.UserService;

public class NotificationServiceDAO implements NotificationService {

	@Override
	public Map<UUID, Notification> readNotifications(String userId) {
		Notification notification = null;
		Map<UUID, Notification> notifications = new LinkedHashMap<UUID, Notification>();

		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "Select * from notifications where user = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet set = stmt.executeQuery();
			
			while (set.next()) {
				notification = readNotification(userId, set.getString("id"));
				notifications.put(notification.getId(), notification);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifications;
	}
	
	@Override
	public Notification readNotification(String userId, String notificationId){
		Notification notification = new Notification();
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = "Select * from notifications where user = ? and id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, notificationId);

			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				notification.setId(UUID.fromString(set.getString("id")));
				notification.setMessage(set.getString("message"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification;
	}

	@Override
	public void addNotification(String userId, Notification notification) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "insert into notifications (id, user, message) " +
					"values (?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, notification.getId().toString());
			stmt.setString(2, userId);
			stmt.setString(3, notification.getMessage());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeNotification(String userId, String id) {
		try {
			Connection conn = DBConnection.getConnectionToDatabase();
			
			String sql = "delete from notifications where user = ? and id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Observer> getObservers(String userId, String tierId) {
		User observer;
		ArrayList<Observer> observers = new ArrayList<>();
		UserService users = new UserServiceDAO();
		
		try {
			Connection conn = DBConnection.getConnectionToDatabase();

			String sql = 
					"select * from tier_members tm " +
					"left join user u " +
					"on tm.userId = u.id " +
					"where tm.userId <> ? " +
					"and tm.tierId = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, tierId);
			ResultSet set = stmt.executeQuery();

			while (set.next()) {
				observer = users.readUser(set.getString("userId"));
				observers.add(observer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return observers;
	}

}

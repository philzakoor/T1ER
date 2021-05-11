package services;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import beans.Notification;
import observer.Observer;

public interface NotificationService {
	public Map<UUID, Notification> readNotifications(String userId);
	public ArrayList<Observer> getObservers(String userId, String tierId);
	void removeNotification(String userId, String notification);
	Notification readNotification(String userId, String notificationId);
	void addNotification(String userId, Notification notification);
}

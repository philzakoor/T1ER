package beans;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import dao.NotificationServiceDAO;
import observer.Observer;
import services.NotificationService;

public class User implements Observer{

	private String password, email;
	private Timestamp createdDate, deletedDate;
	private UUID id;
	private boolean isActivated;

	public User(String password, String email) {
		this(password, email, new Timestamp(Instant.now().toEpochMilli()), UUID.randomUUID());
	}
	
	public User(String password, String email, Timestamp createdDate, UUID id) {
		this(password, email, createdDate, null, id, false);
	}

	public User(String password, String email, Timestamp createdDate, Timestamp deletedDate, UUID id, boolean isActivated) {
		this.password = password;
		this.email = email;
		this.createdDate = createdDate;
		this.deletedDate = deletedDate;
		this.id = id;
		this.isActivated = isActivated;
	}

	public User() {
		password = null;
		email = null;
		createdDate = null;
		deletedDate = null;
		id = null;
		isActivated = false;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setID(UUID id) {
		this.id = id;
	}

	public void setCreateDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setDeleteDate(Timestamp deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	public void setIsActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public UUID getId() {
		return id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getDeletedDate() {
		return deletedDate;
	}
	
	public boolean getIsActivated() {
		return isActivated;
	}
	
	public String toString() {
		return String.format("%s%n%s%n%s", email, password, id, createdDate);
	}
	

	@Override
	public void update(Notification notification) {
		NotificationService service = new NotificationServiceDAO();
		service.addNotification(id.toString(), notification);		
	}
}

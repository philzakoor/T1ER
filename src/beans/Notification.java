package beans;

import java.util.UUID;

public class Notification {
	private UUID id;
	private String message;

	public Notification() {
		id = null;
		message = null;
	}

	public Notification(String message) {
		this.id = UUID.randomUUID();
		this.message = message;
	}

	public Notification(String message, UUID id) {
		this.id = id;
		this.message = message;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UUID getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
}

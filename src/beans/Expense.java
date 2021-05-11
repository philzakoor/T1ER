package beans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class Expense {

	private String name, description;
	private UUID id;
	private BigDecimal amount;
	private User owner;
	private Timestamp createdDate;

	public Expense(String name, String description, UUID id, BigDecimal amount, User owner, Timestamp createdDate) {
		this.name = name;
		this.description = description;
		if (id == null)
			id = UUID.randomUUID();
		this.id = id;
		this.amount = amount;
		this.owner = owner;
		if (createdDate == null)
			createdDate = new Timestamp(Instant.now().toEpochMilli());
		this.createdDate = createdDate;
	}

	public Expense() {
		name = null;
		description = null;
		id = null;
		amount = null;
		owner = null;
		createdDate = null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public User getOwner() {
		return owner;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
}

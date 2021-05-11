package builders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import beans.Expense;
import beans.User;

public class ExpenseBuilder {

	private String name, description;
	private UUID id;
	private User owner;
	private BigDecimal amount;
	private Timestamp createdDate;
	
	public ExpenseBuilder() {
	}
	
	public ExpenseBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ExpenseBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public ExpenseBuilder setOwner(User owner) {
		this.owner = owner;
		return this;
	}
	
	public ExpenseBuilder setAmount(BigDecimal amount) {
		this.amount = amount;
		return this;
	}
	
	public ExpenseBuilder setId(UUID id) {
		this.id = id;
		return this;
	}
	
	public ExpenseBuilder setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	
	public Expense createExpense() {
		return new Expense(name, description, id, amount, owner, createdDate);
	}
}

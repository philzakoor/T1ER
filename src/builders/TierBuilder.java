package builders;

import java.util.ArrayList;
import java.util.UUID;

import beans.Expense;
import beans.Tier;
import beans.User;

public class TierBuilder {
	private String name;
	private UUID id;
	private User owner;
	private ArrayList<User> members;
	private ArrayList<Expense> expenses;
	
	public TierBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public TierBuilder setId(UUID id) {
		this.id = id;
		return this;
	}
	
	public TierBuilder setOwner(User owner) {
		this.owner = owner;
		return this;
	}
	
	public TierBuilder setMembers(ArrayList<User> members) {
		this.members = members;
		return this;
	}
	
	public TierBuilder setExpenses(ArrayList<Expense> expenses) {
		this.expenses = expenses;
		return this;
	}
	
	public Tier createTier() {
		return new Tier(name, id, owner, members, expenses);
	}
}

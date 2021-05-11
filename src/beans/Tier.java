package beans;

import java.util.ArrayList;

import observer.Observable;
import observer.Observer;
import java.util.UUID;

public class Tier implements Observable{
	private String name;
	private UUID id;
	private User owner;
	private ArrayList<User> members;
	private ArrayList<Expense> expenses;
	private ArrayList<Observer> observers;
	
	public Tier(String name, UUID id, User owner, ArrayList<User> members, ArrayList<Expense> expenses) {
		this.name = name;
		if (id == null)
			id = UUID.randomUUID();
		this.id = id;
		this.owner = owner;
		if (members == null) {
			members = new ArrayList<>();
			members.add(owner);
		}
		this.members = members;
		this.expenses = expenses;
	}
	
	public Tier() {
		name = null;
		id = null;
		owner = null;
		members = null;
		expenses = null;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public void setMembers(ArrayList<User> members) {
		this.members = new ArrayList<>(members);
	}
	
	public void setExpenses(ArrayList<Expense> expenses) {
		this.expenses = new ArrayList<>(expenses);
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getId() {
		return id;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public ArrayList<User> getMembers() {
		return members;
	}
	
	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	public User getMember(int member) {
		return members.get(member);
	}
	
	public void addMember(User member) {
		members.add(member);
	}
	
	public void removeMember(User member) {
		members.remove(member);
	}
	
	public void addExpense(Expense expense) {
		expenses.add(expense);
	}
	
	public void removeExpense(Expense expense) {
		expenses.remove(expense);
	}
	
	public String printMembers() {
		String names = "";
		for (User member: members) {
			names += member.getEmail() + " , ";
		}
		return names;
	}
	
	public String toString() {
		return String.format("%n%s%nTier id: %s%nOwner: %s%n", 
				name, id, owner);
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Notification notification) {
		for (Observer observer: observers) {
			observer.update(notification);
		}
	}

	@Override
	public void registerObservers(ArrayList<Observer> o) {
		observers = new ArrayList<>(o);
	}
}

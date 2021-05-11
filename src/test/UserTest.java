package test;

import beans.User;

public class UserTest {
	
	public static void main(String[] args) {
		User user = new User("phil@email.com", "password");
		System.out.println("email: " + user.getEmail() + "\npassword: " + user.getPassword() + "\nid: " + user.getId() +"\nDate created: " + user.getCreatedDate());
		
	}
}

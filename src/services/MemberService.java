package services;

import java.util.ArrayList;

import beans.User;

public interface MemberService {
	void addUser(String email, String tierId);
	void removeUser(String email, String tierId);
	void addOrRemoveUser(String email, String tierId);
	ArrayList<User> readMembers(String tierId);
	User searchMember(String userId, String tierId);
}

package services;

import beans.User;

public interface UserService {

	public void updateUser(User user);
	public void createUser(User user);
	public void createOrUpdateUser(User user);
	public User readUser(String id);
	public void permenentDeleteUser(String id);
	public User searchUser(String email);
	public void activateUser(String key);
	public void resendActivation(User user);
	public User searchKey(String key);
	public void changeUserPassword(User user);
	public void setUserKey(User user, String key);
}
